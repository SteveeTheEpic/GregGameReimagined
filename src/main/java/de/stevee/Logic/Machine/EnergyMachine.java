package de.stevee.Logic.Machine;

public class EnergyMachine extends Machine {
    private final long maxUsage;

    public EnergyMachine(String name, long maxUsage) {
        super(name);
        this.maxUsage = maxUsage;
    }
}
