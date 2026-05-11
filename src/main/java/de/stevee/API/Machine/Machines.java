package de.stevee.API.Machine;

import de.stevee.API.Machine.Module.Module;
import de.stevee.API.Machine.Module.Modules;

import java.util.ArrayList;

public class Machines {

    public static ArrayList<Machine> machines = new ArrayList<>();

    public static Machine NONE = new Machine("Hands").setCount(2);
    public static Machine CRAFTING_TABLE = new Machine("Crafting table");
    public static Machine FURNACE = new Machine("Furnace");
    public static Machine BENDER = new Machine("Bender", 32);
    public static Machine LATHE = new Machine("Lathe", 32);
    public static Machine WIRE_MILL = new Machine("Wire Mill", 32);
    public static Machine TEST_MACHINE = new Machine("Test", 32, 1).setCount(2).addModule(Modules.Test);

}
