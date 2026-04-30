package de.stevee.API.Craft.Modifier;

import de.stevee.API.Craft.Craft;

public class EnergyModifier implements Modifier {
    long constant = 0;
    double multiplier = 0.8;

    public EnergyModifier() {

    }
    public EnergyModifier(double multiplier, long constant) {
        this.multiplier = multiplier;
        this.constant = constant;
    }

    @Override
    public void apply(Craft craft) {
        craft.setRequiredEnergy((long) (craft.requiredEnergy * multiplier + constant));
    }
}
