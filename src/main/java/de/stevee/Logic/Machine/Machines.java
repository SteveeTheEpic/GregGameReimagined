package de.stevee.Logic.Machine;

import java.util.ArrayList;

public class Machines {

    public static ArrayList<Machine> machines = new ArrayList<>();

    public static Machine None = new Machine("Hands").setCount(2);
    public static Machine Crafting_table = new Machine("Crafting table");
    public static Machine Furnace = new Machine("Furnace");

}
