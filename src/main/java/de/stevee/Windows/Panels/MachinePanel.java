package de.stevee.Windows.Panels;

import com.googlecode.lanterna.gui2.*;
import de.stevee.API.Machine.Machine;
import de.stevee.API.Machine.Module.Module;
import de.stevee.API.Render.UI.Component.ScrollingLabel;
import de.stevee.API.Render.DefaultPanel;

import static de.stevee.API.Items.Items.Items_List;


public class MachinePanel extends DefaultPanel {
    private Panel machineInfo = new Panel(new LinearLayout());

    public MachinePanel(String title) {
        super(title);
        machineInfo.withBorder(Borders.singleLine());
        machineInfo.setVisible(false);
        getRoot().addComponent(machineInfo, BorderLayout.Location.CENTER);
    }

    public void refresh() {
        clearItems();
        Items_List.forEach((item) -> {
            item.update();
            if (item.showing && (item instanceof Machine multiMachine)) {
                addItem("%s: %d".formatted(multiMachine.name, multiMachine.quantity), () -> {
                    initPanel(multiMachine);
                });
            }
        });
    }

    private void initPanel(Machine machine) {
        ScrollingLabel label = new ScrollingLabel(machine.name, 10);
        Label count = new Label("Count: %d".formatted(machine.getCount()));
        ActionListBox moduleList = new ActionListBox();

        for (Module module : machine.getModules()) {
            moduleList.addItem(module.getName(), () -> { });
        }

        machineInfo.removeAllComponents();

        machineInfo.addComponent(label);
        machineInfo.addComponent(count);
        machineInfo.addComponent(new Label("Active Modules: "));
        machineInfo.addComponent(moduleList);

        machineInfo.setVisible(true);

        getList().setVisible(false);
        getRoot().invalidate();
    }

    public Panel getMachineInfo() {
        return machineInfo;
    }

    public void hidePanel() {
        machineInfo.setVisible(false);
        getList().setVisible(true);
    }
}
