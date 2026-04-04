package de.stevee.Windows.Panels;

import de.stevee.Logic.Craft.Craft;
import de.stevee.Windows.Panels.Basic.SearchPanel;

import static de.stevee.Logic.Craft.Crafts.crafts;


public class CraftPanel extends SearchPanel<Craft> {
    public CraftPanel(CraftInfoPanel craftInfoPanel) {
        super("Crafts", crafts.values().stream().toList());
        addListener((newIndex, newItem) -> {
            craftInfoPanel.setInformation(crafts.get(newItem.toString()));
        });
    }

    @Override
    public void addResult(String craft) {
        getResults().addItem(craft, () -> crafts.getOrDefault(craft, new Craft("Error").inform("help")).craft());
    }
}

