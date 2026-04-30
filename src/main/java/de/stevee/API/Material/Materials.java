package de.stevee.API.Material;

import static de.stevee.API.Material.Material.FLAGS.*;

public class Materials {
    public static MaterialRegistry materialRegistry = new MaterialRegistry();

    static {
        materialRegistry.register(new Material("Iron").addFlags(ORE, INGOT, ROD, PLATE, HAND_CRAFTABLE).setRequiredTier(2));
        materialRegistry.register(new Material("Copper").addFlags(ORE, INGOT, ROD, PLATE, FOIL, WIRE, FINE_WIRE, HAND_CRAFTABLE)).setRequiredTier(2);
    }
}
