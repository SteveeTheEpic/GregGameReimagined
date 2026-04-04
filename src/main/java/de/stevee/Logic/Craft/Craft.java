package de.stevee.Logic.Craft;

import de.stevee.Logic.Energy.Energy;
import de.stevee.Logic.Items.Item;
import de.stevee.Logic.Machine.Machine;
import de.stevee.Logic.Items.Items;
import de.stevee.Ui.UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static de.stevee.Logic.Machine.Machines.None;
import static de.stevee.Logic.Craft.Crafts.crafts;

public class Craft {
    private final UI ui;

    HashMap<Item, Integer> ingredients = new HashMap<>();
    HashMap<Item, Integer> products = new HashMap<>();

    private final String id;

    public long requiredEnergy = 0;
    public long duration = 0;
    public Machine required = None;
    public boolean machine = true;
    public boolean refund = false;
    public boolean canCraft = true;

    private String errorMsg = "This doesn't exist!";

    public Craft(String id) {
        this.id = id;
        crafts.put(id, this);
        ui = UI.getINSTANCE();
    }

    public void craft() {
        if (ingredients.isEmpty() && products.isEmpty()) {
            inform(errorMsg);
        }

        if (!required.isAvailable()) {
            UI.getINSTANCE().logInfo(required.getName() + " is required!");
            return;
        }

        if (requiredEnergy > Energy.getStored() + Energy.getProduction()) {
            ui.logInfo("Insufficient Energy");
            return;
        }

        if (!hasEnoughIngredients().contains(Items.None)) {
            for (Item item : hasEnoughIngredients()) {
                ui.logInfo("Insufficient %s".formatted(item.name));
            }
            return;
        }

        if (!canCraft) {
            ui.logInfo("Maximum number of this craft running!");
            return;
        }

        useIngredients();
        Energy.subStored(requiredEnergy);

        if (canCraft) {
            canCraft = false;

            ui.getProcessPanel().addProcess(id, duration, () -> {
                products.forEach((item, quantity) -> {
                    item.addQuantity(quantity);
                    ui.logInfo("Made %s %d -> %d".formatted(item.name, item.prevQuantity, item.quantity));
                });

                canCraft = true;
            });
        }
    }

    public Craft addItem(Item item, int quantity) {
        ingredients.put(item, quantity);
        return this;
    }

    public Craft addOutput(Item item, int quantity) {
        products.put(item, quantity);
        return this;
    }

    public Craft requireMachine(Machine machine) {
        required = machine;
        return this;
    }

    /**
     *
     * @return {@code List} of items that are missing
     */
    public List<Item> hasEnoughIngredients() {
        ArrayList<Item> missingItems = new ArrayList<>();
        for (Item item : ingredients.keySet()) {
            if (!((item.quantity - ingredients.get(item)) >= 0 && machine)) {
                missingItems.add(item);
            }
        }

        if (!missingItems.isEmpty()) return missingItems.stream().toList();
        return List.of(Items.None);
    }

    public Craft setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    void refundAll() {
        for (Item item : ingredients.keySet()) {
            item.addQuantity(ingredients.get(item));
        }
    }

    void useIngredients() {
        for (Item item : ingredients.keySet()) {
            item.subQuantity(ingredients.get(item));
        }
    }

    public Craft inform(String text) {
        errorMsg = text;
        return this;
    }

    public String getId() {
        return id;
    }

    public HashMap<Item, Integer> getIngredients() {
        return ingredients;
    }

    public HashMap<Item, Integer> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return id;
    }
}
