package de.stevee.API.Craft;



import de.stevee.API.Material.Material;
import de.stevee.API.Material.Materials;
import de.stevee.Utils.MaterialUtils;

import java.util.HashMap;

import static de.stevee.API.Items.Items.*;
import static de.stevee.API.Machine.Machines.*;
import static de.stevee.API.Material.Material.FLAGS.*;


public class Crafts {

    public static HashMap<String, Craft> crafts = new HashMap<>();

    static {
        Materials.materialRegistry.all().forEach((material -> {
            for (Material.FLAGS flags : Material.FLAGS.values()) {
                if (material.hasFlag(flags)) {
                    if (flags == Material.FLAGS.ORE && material.hasFlag(INGOT)) {
                        new Craft("%s %s".formatted(material.id(), INGOT.displayName())).addItem(MaterialUtils.getMaterial(Material.FLAGS.ORE, material), 1).addOutput(MaterialUtils.getMaterial(INGOT, material), 1).requireMachine(NONE);
                    }

                    if (flags == PLATE && material.hasFlag(INGOT)) {
                        new Craft("%s %s".formatted(material.id(), PLATE.displayName())).addItem(MaterialUtils.getMaterial(INGOT, material), 1).addOutput(MaterialUtils.getMaterial(PLATE, material), 1).requireMachine(BENDER);
                    }

                    if (flags == ROD && material.hasFlag(INGOT)) {
                        new Craft("%s %s".formatted(material.id(), ROD.displayName())).addItem(MaterialUtils.getMaterial(INGOT, material), 1).addOutput(MaterialUtils.getMaterial(ROD, material), 1).requireMachine(LATHE);
                    }

                    if (flags == WIRE && material.hasFlag(INGOT)) {
                        new Craft("%s %s".formatted(material.id(), WIRE.displayName())).addItem(MaterialUtils.getMaterial(INGOT, material), 1).addOutput(MaterialUtils.getMaterial(WIRE, material), 2).requireMachine(WIRE_MILL);
                    }

                    if (flags == FINE_WIRE && material.hasFlag(WIRE)) {
                        new Craft("Fine %s %s".formatted(material.id(), WIRE.displayName())).addItem(MaterialUtils.getMaterial(WIRE, material), 1).addOutput(MaterialUtils.getMaterial(FINE_WIRE, material), 2).requireMachine(WIRE_MILL);
                    }

                    if (flags == FOIL && material.hasFlag(WIRE)) {
                        new Craft("%s %s".formatted(material.id(), FOIL.displayName())).addItem(MaterialUtils.getMaterial(PLATE, material), 1).addOutput(MaterialUtils.getMaterial(FOIL, material), 2).requireMachine(WIRE_MILL);
                    }

                    if (flags == HAND_CRAFTABLE) {
                        if (material.hasFlag(PLATE) && material.hasFlag(INGOT)) {
                            new Craft("Handmade %s %s".formatted(material.id(), PLATE.displayName())).addItem(MaterialUtils.getMaterial(INGOT, material), 1).addOutput(MaterialUtils.getMaterial(PLATE, material), 1).requireMachine(BENDER);
                        }

                        if (material.hasFlag(ROD) && material.hasFlag(INGOT)) {
                            new Craft("Handmade %s %s".formatted(material.id(), ROD.displayName())).addItem(MaterialUtils.getMaterial(INGOT, material), 1).addOutput(MaterialUtils.getMaterial(ROD, material), 1).requireMachine(LATHE);
                        }

                        if (material.hasFlag(WIRE) && material.hasFlag(INGOT)) {
                            new Craft("Handmade %s %s".formatted(material.id(), WIRE.displayName())).addItem(MaterialUtils.getMaterial(INGOT, material), 1).addOutput(MaterialUtils.getMaterial(WIRE, material), 2).requireMachine(WIRE_MILL);
                        }

                        if (material.hasFlag(FINE_WIRE) && material.hasFlag(FOIL)) {
                            new Craft("Handmade Fine %s %s".formatted(material.id(), WIRE.displayName())).addItem(MaterialUtils.getMaterial(FOIL, material), 1).addOutput(MaterialUtils.getMaterial(FINE_WIRE, material), 1).requireMachine(WIRE_MILL);
                        }

                        if (material.hasFlag(FOIL) && material.hasFlag(WIRE)) {
                            new Craft("Handmade %s %s".formatted(material.id(), FOIL.displayName())).addItem(MaterialUtils.getMaterial(PLATE, material), 1).addOutput(MaterialUtils.getMaterial(FOIL, material), 1).requireMachine(WIRE_MILL);
                        }
                    }
                }
            }
        }));
    }

    // Tool Craft
    public static Craft Omnitool_Wooden = new Upgrade("Omnitool Wooden").addItem(Planks, 5).addOutput(Omnitool, 1).requireMachine(CRAFTING_TABLE);

    // Machine Crafts
    public static Craft CraftingTableC = new Craft("Crafting Table").addItem(Planks, 4).addOutput(CRAFTING_TABLE, 1);
    public static Craft FurnaceC = new Craft("Furnace").addItem(Cobblestone, 8).addOutput(FURNACE, 1).requireMachine(CRAFTING_TABLE);

    // Resource Crafts
    public static Craft PlanksC = new Craft("Planks").addItem(Wood, 1).addOutput(Planks, 4).setCraftDuration(1000);
    public static Craft StoneC = new Craft("Stone").addItem(Cobblestone, 1).addOutput(Stone, 1).requireMachine(FURNACE);


}
