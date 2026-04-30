package de.stevee.API.Machine;

import java.util.ArrayList;
import java.util.List;

public class MultiMachine extends EnergyMachine{
    private final int maxModules;
    private ArrayList<Module> modules = new ArrayList<>();

    public MultiMachine(String name, long maxUsage, int maxModules) {
        super(name, maxUsage);
        this.maxModules = maxModules;
    }

    public List<Module> getModules() {
        return modules.stream().toList();
    }

    public MultiMachine addModule(Module module) {
        if (modules.size() < maxModules) {
            modules.add(module);
        }

        return this;
    }

    public void removeModule(Module module) {
        modules.remove(module);
    }

    public MultiMachine setCount(int count) {
        super.quantity = count;
        return this;
    }
}
