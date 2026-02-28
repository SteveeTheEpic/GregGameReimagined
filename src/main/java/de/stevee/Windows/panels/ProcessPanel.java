package de.stevee.Windows.panels;

import com.googlecode.lanterna.gui2.*;
import de.stevee.Logic.Scheduler.Scheduler;
import de.stevee.Main;
import de.stevee.ui.Component.ProgressList;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProcessPanel {
    private final Panel root;
    private final Label header;
    private final ProgressList list;

    public ProcessPanel(String title) {
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

    public void addItem(String label, int time) {
        list.addLabel(label, time);
    }
}
