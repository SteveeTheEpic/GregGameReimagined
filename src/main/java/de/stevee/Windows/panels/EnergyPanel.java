package de.stevee.Windows.panels;

import com.googlecode.lanterna.gui2.*;
import de.stevee.Logic.Energy.Energy;

public class EnergyPanel{
    private final Panel root;
    private static Label energyHeader;
    private final ActionListBox list;

    public EnergyPanel() {
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
