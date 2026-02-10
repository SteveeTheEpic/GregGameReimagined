package de.stevee.Windows.panels;

import com.googlecode.lanterna.gui2.*;
import de.stevee.Logic.Items.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DefaultPanel {
    private final Panel root;
    private final Label header;
    private final ActionListBox list;

    public DefaultPanel(String title) {
        root = new Panel(new BorderLayout());
        header = new Label(title);
        header.setLayoutData(BorderLayout.Location.TOP);

        list = new ActionListBox();

        root.addComponent(header.withBorder(Borders.singleLine()), BorderLayout.Location.TOP);
        root.addComponent(list.withBorder(Borders.singleLine()), BorderLayout.Location.CENTER);
    }

    public Panel getRoot() {
        return root;
    }

    public void focusDefault() {
        list.takeFocus();
    }

    public ActionListBox getList() {
        return list;
    }
}
