package de.stevee.Logic.Machine;

public class EnergyMachine extends Machine {
    private final long usage;

    public EnergyMachine(String name, long usage) {
        super(name);
        this.usage = usage;
    }
}
