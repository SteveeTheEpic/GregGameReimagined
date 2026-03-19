package de.stevee.ui.Component;

import com.googlecode.lanterna.gui2.*;
import de.stevee.Logic.Scheduler.Scheduler;
import de.stevee.Utils.Lists;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ProgressLabel {
    private final Panel root;
    final Label nameLabel;
    final CustomProgressBar bar;
    private final Runnable task;
    private final Future<?> test;

    public ProgressLabel(String text, long maxTicks, Runnable task) {
        this.task = task;
        root = new Panel(new BorderLayout());

        nameLabel = new Label(text);
        bar = new CustomProgressBar(0, maxTicks);
        Scheduler.scheduler.executeAfter(task, maxTicks * 5, TimeUnit.MILLISECONDS);
        bar.setPreferredWidth(40); // length of bar, tweak

        root.addComponent(nameLabel, BorderLayout.Location.LEFT);
        root.addComponent(bar, BorderLayout.Location.RIGHT);
        Lists.progressLabelList.add(this);
        test = Scheduler.scheduler.executeAtFixedRate(this::update, 0, 5, TimeUnit.MILLISECONDS);
    }

    Component getComponent() {
        return root;
    }

    void addProgress(int ticks) {
        bar.setValue(bar.getValue() + ticks);
        bar.invalidate();
    }

    public long getValue() {
        return bar.getValue();
    }

    public void update() {
        root.invalidate();
        nameLabel.invalidate();
        bar.invalidate();
        if (bar.getValue() < bar.getMax())
            bar.setValue(bar.getValue() + 1);
        else {
            test.cancel(true);
            Lists.progressLabelList.remove(this);
        }
    }

    public boolean shouldBeRemoved() {
        return bar.getValue() >= bar.getMax();
    }
}
