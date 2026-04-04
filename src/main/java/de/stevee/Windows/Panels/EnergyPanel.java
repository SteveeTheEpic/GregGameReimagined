package de.stevee.Windows.Panels;

import com.googlecode.lanterna.gui2.*;
import de.stevee.Logic.Energy.Energy;
import de.stevee.Windows.Panels.Basic.DefaultPanel;

public class EnergyPanel extends DefaultPanel {
    private static Label energyHeader = new Label("");
    private final ActionListBox list;

    public EnergyPanel() {
        super(energyHeader.getText());
        energyHeader = new Label("Energy: %s".formatted(Energy.getStored()));
        energyHeader.setLayoutData(BorderLayout.Location.TOP);

        list = new ActionListBox();

        getRoot().addComponent(energyHeader.withBorder(Borders.singleLine()), BorderLayout.Location.TOP);
        getRoot().addComponent(list.withBorder(Borders.singleLine()), BorderLayout.Location.CENTER);
    }

    public void refresh() {
        energyHeader = new Label("Energy: %s".formatted(Energy.getStored()));
    }

    @Override
    public Panel getRoot() {
        return super.getRoot();
    }

    public void focusDefault() {
        list.takeFocus();
    }
}
