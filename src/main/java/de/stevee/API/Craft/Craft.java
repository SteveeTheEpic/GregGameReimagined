package de.stevee.API.Craft;

import de.stevee.API.Craft.Modifier.EnergyModifier;
import de.stevee.API.Craft.Modifier.Modifier;
import de.stevee.API.Energy.Energy;
import de.stevee.API.Items.Item;
import de.stevee.API.Machine.Machine;
import de.stevee.API.Items.Items;
import de.stevee.Ui.UI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static de.stevee.API.Machine.Machines.NONE;
import static de.stevee.API.Craft.Crafts.crafts;

public class Craft {
    private final UI ui;

    HashMap<Item, Integer> ingredients = new HashMap<>();
    HashMap<Item, Integer> products = new HashMap<>();

    private final String id;

    public long requiredEnergy = 0;
    public long craftDuration = 0;
    public Machine requiredMachine = NONE;
    public boolean hasMachine = true;
    public boolean refund = false;

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

        if (!requiredMachine.isAvailable()) {
            UI.getINSTANCE().logInfo(requiredMachine.getName() + " is required!");
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

        useIngredients();
        Energy.subStored(requiredEnergy);

        ui.getProcessPanel().addProcess(id, craftDuration, () -> {
            products.forEach((item, quantity) -> {
                item.addQuantity(quantity);
                ui.logInfo("Made %s %d -> %d".formatted(item.name, item.prevQuantity, item.quantity));
            });


        });
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
        requiredMachine = machine;
        return this;
    }

    /**
     * @return {@code List} of items that are missing
     */
    public List<Item> hasEnoughIngredients() {
        ArrayList<Item> missingItems = new ArrayList<>();
        for (Item item : ingredients.keySet()) {
            if (!((item.quantity - ingredients.get(item)) >= 0 && hasMachine)) {
                missingItems.add(item);
            }
        }

        if (!missingItems.isEmpty()) return missingItems.stream().toList();
        return List.of(Items.None);
    }

    public Craft setCraftDuration(long craftDuration) {
        this.craftDuration = craftDuration;
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

    public Craft setRequiredEnergy(long requiredEnergy) {
        this.requiredEnergy = requiredEnergy;
        return this;
    }

    @Override
    public String toString() {
        return id;
    }
}
