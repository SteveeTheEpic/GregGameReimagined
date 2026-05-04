package de.stevee.API.Machine;

import de.stevee.API.Craft.Modifier.Modifier;

import java.util.ArrayList;
import java.util.Arrays;
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

    public List<Modifier> getAllModifiers() {
        List<Modifier> all = new ArrayList<>();

        getModules().forEach(module -> {
            all.addAll(Arrays.stream(module.getModifiers()).toList());
        });

        return all;
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
