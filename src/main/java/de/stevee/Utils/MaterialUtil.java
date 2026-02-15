package de.stevee.Utils;

import de.stevee.Logic.Items.Item;
import de.stevee.Logic.Material.Material;

import java.util.Objects;

import static de.stevee.Utils.Items.Items_List;

public class MaterialUtil {

    /**
     * Return Item if exists else null
     * @param flag
     * @param material
     * @return {@code  Item} if exists else {@code Items.None}
     */
    public static Item getMaterial(Material.FLAGS flag, Material material) {
        for (Item item : Items_List) {
            if (Objects.equals(item.name, "%s %s".formatted(material.id(), flag.displayName()))) return item;
        }

        return Items.None;
    }
}
