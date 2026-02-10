package de.stevee.Logic.Craft;



import de.stevee.Logic.Controller;
import de.stevee.Logic.Items.Item;
import de.stevee.Logic.Machine.Machine;
import de.stevee.Utils.Crafts;
import de.stevee.ui.UI;

import java.util.ArrayList;
import java.util.List;

import static de.stevee.Logic.Machine.Machines.None;


public class Craft {
    private final UI ui;

    ArrayList<Item> Ingredients = new ArrayList<>();
    ArrayList<Integer> Ingredients_Count = new ArrayList<>();
    ArrayList<Item> Products = new ArrayList<>();
    ArrayList<Integer> Products_Count = new ArrayList<>();

    public Machine required = None;
    public boolean machine = true;
    public boolean refund = false;


    public Craft(String id) {
        Crafts.crafts.put(id, this);
        ui = UI.getINSTANCE();
    }


    public void craft() {
        machine = required.isAvailable();

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
            });
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

    public List<String> getProductsNames() {
        List<String> names = new ArrayList<>();

        for (Item item : Products) {
            names.add(item.name);
        }

        return names;
    }
}
