package de.stevee.Logic.Machine;



import de.stevee.Logic.Items.Item;

import static de.stevee.Logic.Machine.Machines.machines;


public class Machine extends Item {

    public Machine(String name) {
        super(name);
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

    // added for "multithreading"
    public Machine addCount(int count) {
        this.quantity += count;
        return this;
    }
    public Machine subCount(int count) {
        this.quantity -= count;
        return this;
    }
}
