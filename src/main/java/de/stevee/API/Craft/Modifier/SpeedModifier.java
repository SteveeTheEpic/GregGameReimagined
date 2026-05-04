package de.stevee.API.Craft.Modifier;

import de.stevee.API.Craft.Craft;

public class SpeedModifier implements Modifier{
    private double multiplier = 0.8;

    public SpeedModifier() {}
    public SpeedModifier(double multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public void apply(Craft craft) {
        craft.setCraftDuration((long) (craft.getCraftDuration() * multiplier));
    }
}
