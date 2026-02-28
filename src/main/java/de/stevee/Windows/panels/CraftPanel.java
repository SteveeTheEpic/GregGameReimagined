package de.stevee.Windows.panels;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import de.stevee.Logic.Craft.Craft;
import de.stevee.Utils.Items;

import java.util.HashMap;
import java.util.Set;

import static de.stevee.Utils.Crafts.crafts;
import static de.stevee.Utils.Inventory.getMatches;

public class CraftPanel {
    private final Panel root;
    private final TextBox search;
    private final ActionListBox results;

    private HashMap<String, Craft> filtered = new HashMap<>();

    public CraftPanel() {
        root = new Panel(new BorderLayout());

        search = new TextBox(new TerminalSize(30, 1));
        search.setTextChangeListener((newText, changedByUser) -> applyFilter(newText)); // live filter[cite:9]

        results = new ActionListBox();

        root.addComponent(search.withBorder(Borders.singleLine("Search")), BorderLayout.Location.TOP);
        root.addComponent(results.withBorder(Borders.singleLine("Recipes")), BorderLayout.Location.CENTER);

        filtered.putAll(crafts);
        rebuildResults();
    }

    public void refreshInventoryContext() {
        rebuildResults();
    }

    public void focusSearch() {
        // Default: focus search (matches “search field at top” UX)
        search.takeFocus(); // Interactable.takeFocus exists for focus control[cite:50]
    }

    public void focusResults() {
        results.takeFocus();
    }

    public boolean isSearchFocused() {
        return search.isFocused();
    }

    public Panel getRoot() {
        return root;
    }

    private void applyFilter(String q) {
        // clear previous filter results so items are removed when they no longer match
        filtered.clear();
        var matches = getMatches(crafts, q == null ? "" : q);

        // iterate matches (not all crafts) to repopulate filtered
        for (String s : matches) {
            Craft c = crafts.get(s);
            if (c != null) filtered.put(s, c);
        }

        rebuildResults();
    }

    private void rebuildResults() {
        results.clearItems();
        Set<String> r = filtered.keySet();
        
        for (String s : r) {
            Craft craft = filtered.get(s);
            results.addItem(craft.hasEnoughIngredients() == Items.None ? "%s craftable".formatted(s) : "%s not craftable".formatted(s), () -> {
                craft.craft();
                Items.update();
                refreshInventoryContext();
            });
        }
    }
}

