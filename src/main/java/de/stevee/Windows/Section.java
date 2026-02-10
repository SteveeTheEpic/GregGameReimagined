package de.stevee.Windows;

public enum Section {
    FARM("Farm"),
    CRAFT("Craft"),
    INVENTORY("Inventory"),
    QUIT("Quit");

    private final String title;
    Section(String title) { this.title = title; }
    public String title() { return title; }
}
