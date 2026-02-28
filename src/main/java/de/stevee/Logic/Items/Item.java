package de.stevee.Logic.Items;

import de.stevee.Utils.Items;
import de.stevee.ui.UI;

import static de.stevee.Utils.Items.Omnitool;

public class Item {

    public String name;
    public boolean showing;
    public int prev_quantity;
    public int quantity;
    public boolean farmable = false;
    public int requiredTier;

    public Item(String name) {
        this.name = name;
        this.quantity = 0;
        this.requiredTier = 0;
        this.showing = false;
        Items.Items_List.add(this);
    }

    public Item(String name, boolean showing) {
        this.name = name;
        this.quantity = 0;
        this.requiredTier = 0;
        this.showing = showing;
        Items.Items_List.add(this);
    }

    public Item(String name, boolean showing, int requiresTier) {
        this.name = name;
        this.quantity = 0;
        this.farmable = true;
        this.requiredTier = requiresTier;
        this.showing = showing;
        Items.Items_List.add(this);
    }

    public void addQuantity(int Quantity) {
        quantity += Quantity;
    }

    public void subQuantity(int Quantity) {
        quantity -= Quantity;
    }

    public Item setRequiredTier(int count) {
        this.requiredTier = count;
        return  this;
    }

    public Item setShowing(boolean showing) {
        this.showing = showing;
        return this;
    }

    public void update() {
        prev_quantity = quantity;
        if (quantity != 0 || showing) showing = true;
    }

    public void farm() {
        if (Omnitool.tier >= requiredTier) {
            this.update();
            this.addQuantity(1);
            UI.getINSTANCE().logInfo("%s: %d -> %d".formatted(name, prev_quantity, quantity));
        } else {
            UI.getINSTANCE().logInfo("Required Omni Tool tier: %d".formatted(requiredTier));
        }
    }
}
