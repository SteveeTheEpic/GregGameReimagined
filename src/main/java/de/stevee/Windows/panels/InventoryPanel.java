package de.stevee.Windows.panels;

import de.stevee.Logic.Items.Item;
import de.stevee.Logic.Items.Tool;
import de.stevee.Windows.panels.basic.SearchPanel;
import de.stevee.ui.UI;

import java.util.HashMap;
import java.util.Map;

import static de.stevee.Utils.Items.Items_List;

public class InventoryPanel extends SearchPanel<Item> {
    private final UI ui;

    private static Map<String, Item> itemMap = new HashMap<>();

    static {
        Items_List.forEach((item) -> {
            item.update();
            if (item.showing && (item instanceof Tool tool)) {
                itemMap.put("%s's Tier: %d".formatted(tool.name, tool.tier), item);
            }  else if (item.showing) {
                itemMap.put(item.name + ": " + item.quantity, item);
            }
        });
    }

    public InventoryPanel() {
        super("Inventory", itemMap);
        ui = UI.getINSTANCE();
    }

    public void refresh() {
        recalculateResult();
    }
}
