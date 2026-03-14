package de.stevee.ui.Component;

import com.googlecode.lanterna.gui2.*;
import de.stevee.Main;

import java.util.concurrent.TimeUnit;

public class ProgressLabel {
    private final Panel root;
    private final Label nameLabel;
    private final CustomProgressBar bar;
    private final Runnable task;

    ProgressLabel(String text, long maxTicks, Runnable task) {
        this.task = task;
        root = new Panel(new BorderLayout());

        nameLabel = new Label(text);
        bar = new CustomProgressBar(0, maxTicks);
        Main.scheduler.executeAfter(task, maxTicks * 5, TimeUnit.MILLISECONDS);
        bar.setPreferredWidth(40); // length of bar, tweak

        root.addComponent(nameLabel, BorderLayout.Location.LEFT);
        root.addComponent(bar, BorderLayout.Location.RIGHT);
    }

    Component getComponent() {
        return root;
    }

    void addProgress(int ticks) {
        bar.setValue(bar.getValue() + ticks);
    }

    public long getValue() {
        return bar.getValue();
    }

    public long getMax() {
        return bar.getMax();
    }
}
