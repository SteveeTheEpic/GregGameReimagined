package de.stevee.Windows.panels;

import de.stevee.Logic.Craft.Craft;
import de.stevee.Windows.panels.basic.SearchPanel;

import static de.stevee.Utils.Crafts.crafts;

public class CraftPanel extends SearchPanel<Craft> {
    public CraftPanel() {
        super("Crafts", crafts);
    }

    @Override
    public void addResult(String label) {
        getResults().addItem(label, () -> {
            crafts.getOrDefault(label, new Craft("Error").inform("This doesn't exist!")).craft();
        });
    }
}

