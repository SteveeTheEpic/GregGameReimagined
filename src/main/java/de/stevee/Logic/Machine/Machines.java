package de.stevee.Logic.Machine;

import java.util.ArrayList;

public class Machines {

    public static ArrayList<Machine> machines = new ArrayList<>();

    public static Machine None = new Machine("Hands").setCount(2);
    public static Machine Crafting_table = new Machine("Crafting table");
    public static Machine Furnace = new Machine("Furnace");
    public static EnergyMachine BENDER = new EnergyMachine("Bender", 32);
    public static EnergyMachine LATHE = new EnergyMachine("Lathe", 32);
    public static EnergyMachine WIRE_MILL = new EnergyMachine("Wire Mill", 32);

}
