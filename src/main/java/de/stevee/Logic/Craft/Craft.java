package de.stevee.Logic.Craft;



import de.stevee.Logic.Energy.Energy;
import de.stevee.Logic.Items.Item;
import de.stevee.Logic.Machine.Machine;
import de.stevee.Utils.Crafts;
import de.stevee.ui.UI;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static de.stevee.Logic.Machine.Machines.None;


public class Craft {
    private final UI ui;

    ArrayList<Item> Ingredients = new ArrayList<>();
    ArrayList<Integer> Ingredients_Count = new ArrayList<>();
    ArrayList<Item> Products = new ArrayList<>();
    ArrayList<Integer> Products_Count = new ArrayList<>();

    public long requiredEnergy = 0;
    public Machine required = None;
    public boolean machine = true;
    public boolean refund = false;


    public Craft(String id) {
        Crafts.crafts.put(id, this);
        ui = UI.getINSTANCE();
    }


    public void craft() {
        machine = required.isAvailable();

        if (requiredEnergy > Energy.getStored() + Energy.getProduction()) {
            ui.logInfo("Insufficient Energy");
            return;
        }


        Ingredients.forEach((item) -> {
            var Ing_c = Ingredients_Count.get(Ingredients.indexOf(item));

            // Checks if the Items.Item is Craftable or the Machine is available
            if ((item.quantity - Ing_c) >= 0 && machine) {
                item.subQuantity(Ing_c);
            } else if ((item.quantity - Ing_c) < 0){
                ui.logInfo("Insufficient " + item.name);
                item.subQuantity(Ing_c);
                refund = true;
            } else if (!machine) {
                ui.logInfo(required.getName() + " is required!");
            }
        });

        if (refund || !machine) {
            // refunds every item used in the recipe if the craft is refunded
            for (int i = 0; i < Ingredients.size(); i++) {
                Ingredients.get(i).addQuantity(Ingredients_Count.get(i));
            }

        } else {
            // adds the product
            Products.forEach((n) -> {
                n.addQuantity(Products_Count.get(Products.indexOf(n)));
                ui.logInfo("Made " + n.name);
            });
            Energy.tick();

        }
    }

    public boolean craft(boolean simulate) {
        machine = required.isAvailable();

        if (requiredEnergy >= Energy.getStored() + Energy.getProduction()) return false;

        Ingredients.forEach((item) -> {
            var Ing_c = Ingredients_Count.get(Ingredients.indexOf(item));

            // Checks if the Items.Item is Craftable or the Machine is available
            if ((item.quantity - Ing_c) >= 0 && machine) {
                item.subQuantity(Ing_c);
            } else if ((item.quantity - Ing_c) < 0){
                ui.logInfo("Insufficient " + item.name);
                item.subQuantity(Ing_c);
                refund = true;
            } else if (!machine) {
                ui.logInfo(required.getName() + " is required!");
            }
        });

        if (refund || !machine) {
            // refunds every item used in the recipe if the craft is refunded
            for (int i = 0; i < Ingredients.size(); i++) {
                Ingredients.get(i).addQuantity(Ingredients_Count.get(i));
            }

            return false;
        } else {
            // adds the product
            Products.forEach((n) -> {
                n.addQuantity(Products_Count.get(Products.indexOf(n)));
            });
            return true;
        }
    }

    public Craft addItem(Item item, int quantity) {
        Ingredients.add(item);
        Ingredients_Count.add(quantity);
        return this;
    }

    public Craft addOutput(Item item, int quantity) {
        Products.add(item);
        Products_Count.add(quantity);
        return this;
    }

    public Craft requireMachine(Machine machine) {
        required = machine;
        return this;
    }

    public boolean isCraftable() {
        AtomicBoolean refund = new AtomicBoolean(false);
        Ingredients.forEach((item) -> {
            var Ing_c = Ingredients_Count.get(Ingredients.indexOf(item));

            // Checks if the Items.Item is Craftable or the Machine is available
            if ((item.quantity - Ing_c) >= 0 && machine) {
                refund.set(false);
            } else if ((item.quantity - Ing_c) < 0){
                refund.set(true);
            } else if (!machine) {
                refund.set(true);
            }
        });
        return !refund.get();
    }
}
