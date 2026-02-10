package de.stevee.Windows.panels;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import de.stevee.Logic.Craft.Craft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Consumer;

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

        rebuildResults();
    }



    public void refreshInventoryContext() {
        // TODO: annotate entries with craftable/not craftable, etc.
    }

    public void focusDefault() {
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
        var matches = getMatches(crafts, q);

        crafts.forEach((s, c) -> {
            if (matches.contains(s)) filtered.put(s, c);
        });

        rebuildResults();
    }

    private void rebuildResults() {
        results.clearItems();
        Set<String> r = filtered.keySet();
        
        for (String s : r) {
            results.addItem(s, filtered.get(s)::craft);
        }
    }
}

