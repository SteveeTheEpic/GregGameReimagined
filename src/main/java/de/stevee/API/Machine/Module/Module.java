package de.stevee.API.Machine.Module;

import de.stevee.API.Craft.Modifier.Modifier;

public class Module {
    String name;
    Modifier[] modifiers;

    public Module(String name, Modifier... modifiers) {
        this.name = name;
        this.modifiers = modifiers;
    }

    public Modifier[] getModifiers() {
        return modifiers;
    }
    public String getName() {
        return name;
    }
}
