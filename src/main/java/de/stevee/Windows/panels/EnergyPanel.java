package de.stevee.Windows.panels;

import com.googlecode.lanterna.gui2.*;
import de.stevee.Logic.Energy.Energy;
import de.stevee.Windows.panels.basic.DefaultPanel;

public class EnergyPanel extends DefaultPanel {
    private final Panel root;
    private static Label energyHeader = new Label("");
    private final ActionListBox list;

    public EnergyPanel() {
        super(energyHeader.getText());
        root = new Panel(new BorderLayout());
        energyHeader = new Label("Energy: %s".formatted(Energy.getStored()));
        energyHeader.setLayoutData(BorderLayout.Location.TOP);

        list = new ActionListBox();

        root.addComponent(energyHeader.withBorder(Borders.singleLine()), BorderLayout.Location.TOP);
        root.addComponent(list.withBorder(Borders.singleLine()), BorderLayout.Location.CENTER);
    }

    public void refresh() {
        energyHeader = new Label("Energy: %s".formatted(Energy.getStored()));
    }


    public Panel getRoot() {
        return root;
    }

    public void focusDefault() {
        list.takeFocus();
    }
}
