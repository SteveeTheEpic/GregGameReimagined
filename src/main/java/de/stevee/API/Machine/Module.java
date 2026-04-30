package de.stevee.API.Machine;

public class Module {
    String name;
    boolean available;
    public Module(String name) {
        this.name = name;
        available = false;
    }

    public String getName() {
        return name;
    }
}
