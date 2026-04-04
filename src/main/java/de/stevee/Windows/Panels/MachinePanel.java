package de.stevee.Windows.Panels;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import de.stevee.Logic.Machine.Machine;
import de.stevee.Logic.Machine.Module;
import de.stevee.Logic.Machine.MultiMachine;
import de.stevee.Ui.Component.ScrollingLabel;
import de.stevee.Windows.Panels.Basic.DefaultPanel;

import static de.stevee.Logic.Items.Items.Items_List;


public class MachinePanel extends DefaultPanel {
    private Panel toBeRemoved = null;
    public MachinePanel(String title) {
        super(title);
    }

    public void refresh() {
        clearItems();
        Items_List.forEach((item) -> {
            item.update();
            if (item.showing && (item instanceof MultiMachine multiMachine)) {
                addItem("%s: %d".formatted(multiMachine.name, multiMachine.quantity), () -> {
                    initPanel(multiMachine);
                });
                return;
            }
            if (item.showing && (item instanceof Machine machine)) {
                addItem(machine.name + ": " + machine.quantity, () -> {});
            }
        });
    }

    private void initPanel(MultiMachine machine) {
        Panel panel = new Panel();
        ScrollingLabel label = new ScrollingLabel(machine.name, 10);
        Label count = new Label("Count: %d".formatted(machine.getCount()));
        ActionListBox moduleList = new ActionListBox();

        for (Module module : machine.getModules()) {
            moduleList.addItem(module.getName(), () -> {});
        }

        panel.addComponent(label);
        panel.addComponent(count);
        panel.addComponent(new Label("Active Modules: "));
        panel.addComponent(moduleList);

        getRoot().addComponent(panel.withBorder(Borders.singleLine()), BorderLayout.Location.CENTER);
        toBeRemoved = panel;
    }

    public void removePanel() {
        getRoot().removeComponent(toBeRemoved);
    }
}
