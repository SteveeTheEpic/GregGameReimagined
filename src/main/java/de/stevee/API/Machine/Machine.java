package de.stevee.API.Machine;



import de.stevee.API.Craft.Modifier.Modifier;
import de.stevee.API.Items.Item;
import de.stevee.API.Machine.Module.Module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static de.stevee.API.Machine.Machines.machines;


public class Machine extends Item {
    List<Machine> machines = new ArrayList<>();
    private ArrayList<Module> modules = new ArrayList<>();
    private int maxModules = 0;
    private long maxUsage = 0;

    public Machine(String name) {
        super(name);
        machines.add(this);
    }

    public Machine(String name, long maxUsage) {
        super(name);
        this.maxUsage = maxUsage;
        machines.add(this);
    }

    public Machine(String name, long maxUsage, int maxModules) {
        super(name);
        this.maxUsage = maxUsage;
        this.maxModules = maxModules;
        machines.add(this);
    }

    public Boolean isAvailable() {
        return this.quantity > 0;
    }
    public String getName() {
        return this.name;
    }
    public Integer getCount() {
        return this.quantity;
    }
    public Machine setCount(int count) {
        this.quantity = count;
        return this;
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

    public Machine addModule(Module module) {
        if (modules.size() < maxModules) {
            modules.add(module);
        }

        return this;
    }

    public void removeModule(Module module) {
        modules.remove(module);
    }
}
