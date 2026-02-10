package de.stevee.Windows.panels;

import de.stevee.Logic.Items.Tool;
import de.stevee.Logic.Machine.Machine;

import static de.stevee.Utils.Items.Items_List;

public class MachinePanel extends DefaultPanel{
    public MachinePanel(String title) {
        super(title);
    }

    public void refresh() {
        getList().clearItems();
        Items_List.forEach((item) -> {
            item.update();
            if (item.showing && (item instanceof Tool tool)) {
            } else if (item.showing && (item instanceof Machine machine)) {
                getList().addItem(machine.name + ": " + machine.quantity, () -> {});
            } else if (item.showing) {
            }
        });
    }
}
