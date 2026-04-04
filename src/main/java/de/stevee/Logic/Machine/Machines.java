package de.stevee.Logic.Machine;

import java.util.ArrayList;

public class Machines {

    public static ArrayList<Machine> machines = new ArrayList<>();

    public static Machine NONE = new Machine("Hands").setCount(2);
    public static Machine CRAFTING_TABLE = new Machine("Crafting table");
    public static Machine FURNACE = new Machine("Furnace");
    public static EnergyMachine BENDER = new EnergyMachine("Bender", 32);
    public static EnergyMachine LATHE = new EnergyMachine("Lathe", 32);
    public static EnergyMachine WIRE_MILL = new EnergyMachine("Wire Mill", 32);
    public static MultiMachine TEST_MACHINE = new MultiMachine("Test", 32, 1).setCount(2).addModule(new Module("test"));

}
