package de.stevee.Utils;



import de.stevee.Logic.Craft.Craft;
import de.stevee.Logic.Craft.Upgrade;
import de.stevee.Logic.Material.Material;
import de.stevee.Logic.Material.Materials;

import java.util.HashMap;

import static de.stevee.Logic.Machine.Machines.None;
import static de.stevee.Logic.Material.Material.FLAGS.*;
import static de.stevee.Utils.Items.*;
import static de.stevee.Logic.Machine.Machines.*;


public class Crafts {

    public static HashMap<String, Craft> crafts = new HashMap<>();

    static {
        Materials.materialRegistry.all().forEach((material -> {
            for (Material.FLAGS flags : Material.FLAGS.values()) {
                if (material.hasFlag(flags)) {
                    if (flags == Material.FLAGS.ORE && material.hasFlag(INGOT)) {
                        new Craft("%s %s".formatted(material.id(), INGOT.displayName())).addItem(MaterialUtil.getMaterial(Material.FLAGS.ORE, material), 1).addOutput(MaterialUtil.getMaterial(INGOT, material), 1).requireMachine(None);
                    }

                    if (flags == PLATE && material.hasFlag(INGOT)) {
                        new Craft("%s %s".formatted(material.id(), PLATE.displayName())).addItem(MaterialUtil.getMaterial(INGOT, material), 1).addOutput(MaterialUtil.getMaterial(PLATE, material), 1).requireMachine(BENDER);
                    }

                    if (flags == ROD && material.hasFlag(INGOT)) {
                        new Craft("%s %s".formatted(material.id(), ROD.displayName())).addItem(MaterialUtil.getMaterial(INGOT, material), 1).addOutput(MaterialUtil.getMaterial(ROD, material), 1).requireMachine(LATHE);
                    }

                    if (flags == WIRE && material.hasFlag(INGOT)) {
                        new Craft("%s %s".formatted(material.id(), WIRE.displayName())).addItem(MaterialUtil.getMaterial(INGOT, material), 1).addOutput(MaterialUtil.getMaterial(WIRE, material), 2).requireMachine(WIRE_MILL);
                    }

                    if (flags == FINE_WIRE && material.hasFlag(WIRE)) {
                        new Craft("Fine %s %s".formatted(material.id(), WIRE.displayName())).addItem(MaterialUtil.getMaterial(WIRE, material), 1).addOutput(MaterialUtil.getMaterial(FINE_WIRE, material), 2).requireMachine(WIRE_MILL);
                    }
                }
            }
        }));
    }

    // Tool Craft
    public static Craft Omnitool_Wooden = new Upgrade("Omnitool Wooden").addItem(Planks, 5).addOutput(Omnitool, 1).requireMachine(Crafting_table);

    // Machine Crafts
    public static Craft CraftingTableC = new Craft("Crafting Table").addItem(Planks, 4).addOutput(Crafting_table, 1);
    public static Craft FurnaceC = new Craft("Furnace").addItem(Cobblestone, 8).addOutput(Furnace, 1).requireMachine(Crafting_table);

    // Resource Crafts
    public static Craft PlanksC = new Craft("Planks").addItem(Wood, 1).addOutput(Planks, 4);
    public static Craft StoneC = new Craft("Stone").addItem(Cobblestone, 1).addOutput(Stone, 1).requireMachine(Furnace);


}
