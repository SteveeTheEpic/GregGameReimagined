package de.stevee.API.Craft.Modifier;

import de.stevee.API.Craft.Craft;
import de.stevee.API.Items.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InputModifier implements Modifier{
    private double multiplier = 0.8;
    private final HashMap<Item, Integer> modified = new HashMap<>();

    public InputModifier() {}
    public InputModifier(double multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public void apply(Craft craft) {
        for (Map.Entry<Item, Integer> pair : craft.getIngredients().entrySet()) {
            modified.put(pair.getKey(), (int) (pair.getValue() * multiplier));
        }
        craft.setIngredients(modified);
    }
}
