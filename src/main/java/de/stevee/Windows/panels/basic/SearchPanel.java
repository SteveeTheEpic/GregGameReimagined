package de.stevee.Windows.panels.basic;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.stevee.Utils.Inventory.getMatches;

public class SearchPanel<T> {
    private final Panel root;
    private final TextBox search;
    private final ActionListBox results;
    private int prev_idx = 0;

    private HashMap<String, T> all = new HashMap<>();
    private ArrayList<String> filtered = new ArrayList<>();

    public SearchPanel(String title, Map<String, T> labels) {
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

    public void focusSearch() {
        search.takeFocus();
    }

    public void focusResults() {
        results.takeFocus();
    }

    public void focusDefault(){
        results.takeFocus();
    }

    public boolean isSearchFocused() {
        return search.isFocused();
    }

    public Panel getRoot() {
        return root;
    }

    private void applyFilter(String string) {
        filtered.clear();
        var matches = getMatches(all, string == null || string.isBlank() ? "" : string);

        filtered.addAll(matches);

        recalculateResult();
    }

    public ActionListBox getResults() {
        return results;
    }

    public void recalculateResult() {
        results.clearItems();
        List<String> r = filtered.stream().toList();

        for (String l : r) {
            addResult(l);
        }
    }

    public void addResult(String label) {
        results.addItem(label, () -> {});
    }

    public T getActive() {
        return all.get(filtered.get(results.getSelectedIndex()));
    }
}
