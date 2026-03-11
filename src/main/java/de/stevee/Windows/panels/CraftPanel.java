package de.stevee.Windows.panels;

import com.googlecode.lanterna.gui2.*;
import de.stevee.Logic.Craft.Craft;
import de.stevee.Logic.Items.Item;
import de.stevee.Windows.panels.basic.SearchPanel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.stevee.Utils.Crafts.crafts;

public class CraftPanel extends SearchPanel<Craft> {
    private static HashMap<Label, Craft> labels = new HashMap<>();

    static {
        for (Map.Entry<String, Craft> s : crafts.entrySet()) {
            labels.put(new Label(s.getKey()), s.getValue());
        }
    }

    public CraftPanel() {
        super("Crafts", labels);
    }

    @Override
    public void addResult(String label) {
        getResults().addItem(label, () -> {
            labels.getOrDefault(new Label(label), new Craft("Error").inform("This doesn't exist!")).craft();
        });
    }
}

