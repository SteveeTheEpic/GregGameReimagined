package de.stevee.Utils;



import de.stevee.Logic.Craft.Craft;
import de.stevee.Logic.Craft.Upgrade;

import java.util.HashMap;

import static de.stevee.Utils.Items.*;
import static de.stevee.Logic.Machine.Machines.*;


public class Crafts {

    public static HashMap<String, Craft> crafts = new HashMap<>();

    // Tool Craft
    public static Craft Omnitool_Wooden = new Upgrade("Omnitool Wooden").addItem(Planks, 5).addOutput(Omnitool, 1).requireMachine(Crafting_table);

    // Machine Crafts
    public static Craft CraftingTableC = new Craft("Crafting Table").addItem(Planks, 4).addOutput(Crafting_table, 1);
    public static Craft FurnaceC = new Craft("Furnace").addItem(Cobblestone, 8).addOutput(Furnace, 1).requireMachine(Crafting_table);

    // Resource Crafts
    public static Craft PlanksC = new Craft("Planks").addItem(Wood, 1).addOutput(Planks, 4);
    public static Craft StoneC = new Craft("Stone").addItem(Cobblestone, 1).addOutput(Stone, 1).requireMachine(Furnace);


}
