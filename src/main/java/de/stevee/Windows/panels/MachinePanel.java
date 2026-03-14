package de.stevee.Windows.panels;

import de.stevee.Logic.Machine.Machine;
import de.stevee.Windows.panels.basic.DefaultPanel;

import static de.stevee.Utils.Items.Items_List;


public class MachinePanel extends DefaultPanel {
    public MachinePanel(String title) {
        super(title);
    }

    public void refresh() {
        getList().clearItems();
        Items_List.forEach((item) -> {
            item.update();
            if (item.showing && (item instanceof Machine machine)) {
                getList().addItem(machine.name + ": " + machine.quantity, () -> {});
            }
        });
    }
}
