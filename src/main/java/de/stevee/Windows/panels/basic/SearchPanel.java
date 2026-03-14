package de.stevee.Windows.panels.basic;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import de.stevee.Windows.panels.InfoPanel;
import de.stevee.ui.Component.HoverActionListBox;

import java.util.*;

import static de.stevee.Utils.Crafts.crafts;
import static de.stevee.Utils.Inventory.getMatches;

public class SearchPanel<T> {
    private final Panel root;
    private final TextBox search;
    private final HoverActionListBox results;

    private ArrayList<T> all = new ArrayList<>();
    private ArrayList<String> filtered = new ArrayList<>();

    public SearchPanel(String title, List<T> labels) {
        root = new Panel(new BorderLayout());

        search = new TextBox(new TerminalSize(30, 1));
        search.setTextChangeListener((newText, changedByUser) -> applyFilter(newText));

        results = new HoverActionListBox();

        all.addAll(labels);
        all.forEach((s) -> {
            filtered.add(s.toString());
        });

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
        ArrayList<String> matches = getMatches(all, string == null || string.isBlank() ? "" : string);

        filtered.addAll(matches);

        recalculateResult();
    }

    public HoverActionListBox getResults() {
        return results;
    }

    public void recalculateResult() {
        results.clearItems();
        List<String> r = filtered.stream().toList();

        for (String l : r) {
            addResult(l);
        }
    }

    public void addListener(HoverActionListBox.Listener listener) {
        results.addListener(listener);
    }

    public void addResult(String t) {
        results.addItem(t, () -> {});
    }
}
