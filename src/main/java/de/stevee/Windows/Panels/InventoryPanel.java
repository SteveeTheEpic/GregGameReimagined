package de.stevee.Windows.Panels;

import de.stevee.API.Items.Tool;
import de.stevee.API.Render.SearchPanel;
import de.stevee.Ui.UI;

import java.util.ArrayList;

import static de.stevee.API.Items.Items.Items_List;


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
