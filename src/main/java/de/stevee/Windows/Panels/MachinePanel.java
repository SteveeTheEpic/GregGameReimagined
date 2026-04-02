package de.stevee.Windows.Panels;

import de.stevee.Logic.Machine.Machine;
import de.stevee.Windows.Panels.Basic.DefaultPanel;

import static de.stevee.Utils.Items.Items_List;


public class MachinePanel extends DefaultPanel {
    public MachinePanel(String title) {
        super(title);
    }

    public void refresh() {
        clearItems();
        Items_List.forEach((item) -> {
            item.update();
            if (item.showing && (item instanceof Machine machine)) {
                addItem(machine.name + ": " + machine.quantity, () -> {});
            }
        });
    }
}
