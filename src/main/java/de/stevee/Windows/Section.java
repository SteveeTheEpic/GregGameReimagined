package de.stevee.Windows;

public enum Section {
    FARM("Farm"),
    CRAFT("Craft"),
    PROCESS("Process"),
    INVENTORY("Inventory"),
    ENERGY("Energy"),
    MACHINES("Machines"),
    QUIT("Quit");

    private final String title;
    Section(String title) { this.title = title; }
    public String title() { return title; }
}
