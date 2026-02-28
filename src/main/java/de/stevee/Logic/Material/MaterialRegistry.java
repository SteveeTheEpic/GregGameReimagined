package de.stevee.Logic.Material;

import de.stevee.Logic.Items.Item;
import de.stevee.Utils.Items;

import java.util.*;

import static de.stevee.Logic.Material.Material.FLAGS.*;

public class MaterialRegistry {
    private final Map<String, Material> materials = new HashMap<>();

    public Material register(Material m) {
        if (materials.containsKey(m.id()))
            throw new IllegalArgumentException("Duplicate material: " + m.id());
        materials.put(m.id(), m);
        for (Material.FLAGS flag : m.flags()) {
            if (m.getRequiredTier() > 0) {
                Items.Items_List.add(new Item("%s %s".formatted(m.id(), flag.displayName())).setRequiredTier(m.getRequiredTier()));
            }
            Items.Items_List.add(new Item("%s %s".formatted(m.id(), flag.displayName())));
        }
        return m;
    }

    public Material get(String id) {
        return materials.get(id);
    }

    public Collection<Material> all() {
        return Collections.unmodifiableCollection(materials.values());
    }
}
