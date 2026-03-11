package de.stevee.Windows.panels.basic;

import com.googlecode.lanterna.gui2.BorderLayout;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import de.stevee.ui.Component.ProgressList;

public class ProgressPanel {
    private final Panel root;
    private final Label header;
    private static ProgressList list = null;

    public ProgressPanel(String title) {
        root = new Panel(new BorderLayout());
        header = new Label(title);
        header.setLayoutData(BorderLayout.Location.TOP);

        list = new ProgressList();

        root.addComponent(header.withBorder(Borders.singleLine()), BorderLayout.Location.TOP);
        root.addComponent(list.getComponent().withBorder(Borders.singleLine()), BorderLayout.Location.CENTER);
    }

    public Panel getRoot() {
        return root;
    }

    public void focusDefault() {
        list.getComponent();
    }

    public ProgressList getList() {
        return list;
    }

    public static void addProcess(String label, long time) {
        list.addLabel(label, time);
    }
}
