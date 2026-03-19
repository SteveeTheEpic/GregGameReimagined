package de.stevee.Windows.panels.basic;

import com.googlecode.lanterna.gui2.*;
import de.stevee.ui.Component.ProgressLabel;
import de.stevee.ui.Component.ProgressList;

public class ProgressPanel {
    private final Panel root;
    private final Label header;
    private final ProgressList list;

    public ProgressPanel(String title) {
        root = new Panel(new BorderLayout());
        header = new Label(title);
        header.setLayoutData(BorderLayout.Location.TOP);

        list = new ProgressList();

        root.addComponent(header.withBorder(Borders.singleLine()), BorderLayout.Location.TOP);
        root.addComponent(list.withBorder(Borders.singleLine()), BorderLayout.Location.CENTER);
    }

    public Panel getRoot() {
        return root;
    }

    public void focusDefault() {
        list.takeFocus();
    }

    public ProgressList getList() {
        return list;
    }

    public void addProcess(String label, long time, Runnable task) {
        list.addItem(new ProgressLabel(label, time, task));
    }
}
