package de.stevee.Windows.panels.basic;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.stevee.Utils.Inventory.getMatchesInList;

public class SearchPanel<T> {
    private final Panel root;
    private final TextBox search;
    private final ActionListBox results;

    private HashMap<Label, T> all = new HashMap<>();
    private ArrayList<Label> filtered = new ArrayList<>();

    public SearchPanel(String title, Map<Label, T> labels) {
        root = new Panel(new BorderLayout());

        search = new TextBox(new TerminalSize(30, 1));
        search.setTextChangeListener((newText, changedByUser) -> applyFilter(newText));

        results = new ActionListBox();

        all.putAll(labels);
        filtered.addAll(all.keySet());

        root.addComponent(search.withBorder(Borders.singleLine("Search")), BorderLayout.Location.TOP);
        root.addComponent(results.withBorder(Borders.singleLine(title)), BorderLayout.Location.CENTER);

        recalculateResult();
    }

    public void refreshInventoryContext() {
        recalculateResult();
    }

    public void focusSearch() {
        search.takeFocus();
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
        filtered.clear();
        var matches = getMatchesInList(all.keySet().stream().toList(), q == null || q.isBlank() ? "" : q);

        for (String s : matches) {
            filtered.add(new Label(s));
        }

        recalculateResult();
    }

    public ActionListBox getResults() {
        return results;
    }

    private void recalculateResult() {
        results.clearItems();
        List<Label> r = filtered.stream().toList();

        for (Label l : r) {
            addResult(l.getText());
        }
    }

    public void addResult(String label) {
        results.addItem(label, () -> {});
    }
}
