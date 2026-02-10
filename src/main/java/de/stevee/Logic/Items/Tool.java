package de.stevee.Logic.Items;

public class Tool extends Item{
    public int prev_tier;
    public int tier;

    public Tool(String name, int tier) {
        super(name);
        this.tier = tier;
        this.quantity = 1;
    }

    public void addTier(int tiers) {
        this.tier += tiers;
    }
    public void setTier(int tier) {
        this.tier = tier;
    }
}
