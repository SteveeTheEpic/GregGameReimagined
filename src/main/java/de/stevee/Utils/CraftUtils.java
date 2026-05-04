package de.stevee.Utils;

import de.stevee.API.Craft.Craft;
import de.stevee.API.Items.Item;
import de.stevee.API.Items.Items;

import java.util.ArrayList;
import java.util.List;

public class CraftUtils {

    /**
     * @return {@code List} of items that are missing
     */
    public static List<Item> hasEnoughIngredients(Craft craft) {
        ArrayList<Item> missingItems = new ArrayList<>();
        for (Item item : craft.getIngredients().keySet()) {
            if (!((item.quantity - craft.getIngredients().get(item)) >= 0 && craft.hasMachine)) {
                missingItems.add(item);
            }
        }

        if (!missingItems.isEmpty()) return missingItems.stream().toList();
        return List.of(Items.None);
    }
}
