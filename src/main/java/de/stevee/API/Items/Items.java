package de.stevee.API.Items;

import java.util.ArrayList;

public class Items {
    public static ArrayList<Item> Items_List = new ArrayList<>();

    public static Item None = new Item("Error");

    public static Tool Omnitool = new Tool("Omni Tool", 0);

    public static Item Cobblestone = new Item("Cobblestone", true, 1);
    public static Item Wood = new Item("Wood", true, 0);
    public static Item Planks = new Item("Planks");
    public static Item Stone = new Item("Stone");


    public static void update() {
        for (Item item : Items_List) {
            item.update();
        }
    }
}