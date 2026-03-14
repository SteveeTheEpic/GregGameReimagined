package de.stevee.Windows.panels;

import de.stevee.Logic.Items.Item;
import de.stevee.Logic.Items.Tool;
import de.stevee.Windows.panels.basic.SearchPanel;
import de.stevee.ui.UI;

import java.util.ArrayList;

import static de.stevee.Utils.Items.Items_List;


public class InventoryPanel extends SearchPanel<String> {
    private final UI ui;

    private static ArrayList<String> itemMap = new ArrayList<>();

    static {
        Items_List.forEach((item) -> {
            item.update();
            if (item.showing && (item instanceof Tool tool)) {
                itemMap.add("%s Level: %d".formatted(item.name, tool.tier));
            }  else if (item.showing) {
                itemMap.add("%s: %d".formatted(item.name, item.quantity));
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
