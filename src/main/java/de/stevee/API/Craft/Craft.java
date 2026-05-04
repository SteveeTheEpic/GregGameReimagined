package de.stevee.API.Craft;

import de.stevee.API.Energy.Energy;
import de.stevee.API.Items.Item;
import de.stevee.API.Machine.Machine;
import de.stevee.API.Items.Items;
import de.stevee.API.Render.UI.UI;

import java.util.HashMap;

import static de.stevee.API.Machine.Machines.NONE;
import static de.stevee.API.Craft.Crafts.crafts;
import static de.stevee.Utils.CraftUtils.hasEnoughIngredients;

public class Craft {
    private final UI ui = UI.getINSTANCE();

    HashMap<Item, Integer> ingredients = new HashMap<>();
    HashMap<Item, Integer> products = new HashMap<>();

    HashMap<Item, Integer> mIngredients = new HashMap<>();
    HashMap<Item, Integer> mProducts = new HashMap<>();

    private final String id;

    public long requiredEnergy = 0;
    public long modifiedRequiredEnergy = 0;
    public long craftDuration = 0;
    public long modifiedCraftDuration = 0;
    public Machine requiredMachine = NONE;
    public boolean hasMachine = true;
    public boolean refund = false;

    private String errorMsg = "This doesn't exist!";

    public Craft(String id) {
        this.id = id;
        crafts.put(id, this);
    }

    public void craft() {
        if (ingredients.isEmpty() && products.isEmpty()) {
            inform(errorMsg);
            return;
        }

        if (!requiredMachine.isAvailable()) {
            ui.logInfo(requiredMachine.getName() + " is required!");
            return;
        }

        if (modifiedRequiredEnergy > Energy.getStored() + Energy.getProduction()) {
            ui.logInfo("Insufficient Energy");
            return;
        }

        if (!hasEnoughIngredients(this).contains(Items.None)) {
            for (Item item : hasEnoughIngredients(this)) {
                ui.logInfo("Insufficient %s".formatted(item.name));
            }
            return;
        }

        useIngredients();
        Energy.subStored(requiredEnergy);

        ui.getProcessPanel().addProcess(id, modifiedCraftDuration, () -> {
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

    public void setIngredients(HashMap<Item, Integer> ingredients) {
        this.ingredients = ingredients;
    }

    public long getCraftDuration() {
        return craftDuration;
    }

    @Override
    public String toString() {
        return id;
    }
}
