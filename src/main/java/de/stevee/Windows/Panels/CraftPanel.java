package de.stevee.Windows.Panels;

import de.stevee.API.Craft.Craft;
import de.stevee.API.Render.SearchPanel;

import static de.stevee.API.Craft.Crafts.crafts;


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

