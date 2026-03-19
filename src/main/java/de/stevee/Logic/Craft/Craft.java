package de.stevee.Logic.Craft;

import de.stevee.Logic.Energy.Energy;
import de.stevee.Logic.Items.Item;
import de.stevee.Logic.Machine.Machine;
import de.stevee.Utils.Items;
import de.stevee.ui.UI;

import java.util.HashMap;

import static de.stevee.Logic.Machine.Machines.None;
import static de.stevee.Utils.Crafts.crafts;

public class Craft {
    private final UI ui;

    HashMap<Item, Integer> Ingredients = new HashMap<>();
    HashMap<Item, Integer> Products = new HashMap<>();

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
        if (Ingredients.isEmpty() && Products.isEmpty()) {
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

        Item insufficientItem = hasEnoughIngredients();
        if (insufficientItem != Items.None) {
            ui.logInfo("Insufficient " + insufficientItem.name);
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
                Products.forEach((item, quantity) -> {
                    item.addQuantity(quantity);
                    ui.logInfo("Made %s %d -> %d".formatted(item.name, item.prev_quantity, item.quantity));
                });

                canCraft = true;
            });
        }
    }

    public Craft addItem(Item item, int quantity) {
        Ingredients.put(item, quantity);
        return this;
    }

    public Craft addOutput(Item item, int quantity) {
        Products.put(item, quantity);
        return this;
    }

    public Craft requireMachine(Machine machine) {
        required = machine;
        return this;
    }

    public Item hasEnoughIngredients() {
        for (Item item : Ingredients.keySet()) {
            if (!((item.quantity - Ingredients.get(item)) >= 0 && machine)) {
                return item;
            }
        }

        return Items.None;
    }

    public Craft setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    void refundAll() {
        for (Item item : Ingredients.keySet()) {
            item.addQuantity(Ingredients.get(item));
        }
    }

    void useIngredients() {
        for (Item item : Ingredients.keySet()) {
            item.subQuantity(Ingredients.get(item));
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
        return Ingredients;
    }

    public HashMap<Item, Integer> getProducts() {
        return Products;
    }

    @Override
    public String toString() {
        return id;
    }
}
