package de.stevee.Windows.panels;

import de.stevee.Logic.Items.Tool;
import de.stevee.ui.UI;

import static de.stevee.Utils.Items.Items_List;

public class InventoryPanel extends DefaultPanel {
    private final UI ui;

    public InventoryPanel(String title) {
        super(title);
        ui = UI.getINSTANCE();
    }

    public void refresh() {
        getList().clearItems();
        Items_List.forEach((item) -> {
            if (item.showing && (item instanceof Tool tool)) {
                getList().addItem("%s's Tier: %d".formatted(tool.name, tool.tier), () -> {});
            } else {
                if (item.showing) {
                    getList().addItem(item.name + ": " + item.quantity, () -> {});
                }
            }
        });
    }
}
