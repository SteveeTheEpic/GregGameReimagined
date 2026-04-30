package de.stevee.Utils;

import de.stevee.API.Items.Item;
import de.stevee.API.Items.Items;
import de.stevee.API.Material.Material;

import java.util.Objects;

import static de.stevee.API.Items.Items.Items_List;

public class MaterialUtil {

    /**
     * @param flag
     * @param material
     * @return {@code  Items} if exists else {@code Items.None}
     */
    public static Item getMaterial(Material.FLAGS flag, Material material) {
        for (Item item : Items_List) {
            if (Objects.equals(item.name, "%s %s".formatted(material.id(), flag.displayName()))) return item;
        }

        return Items.None;
    }
}
