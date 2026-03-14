package de.stevee.ui.Component;

import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import de.stevee.Utils.Lists;

import java.util.ArrayList;
import java.util.List;

public class ProgressList {
    private  Panel root = null;
    private  final List<ProgressLabel> list = new ArrayList<>();

    public ProgressList() {
        root = new Panel(new LinearLayout(Direction.VERTICAL));
        Lists.progressLists.add(this);
    }

    public Component getComponent() {
        return root;
    }

    public ProgressLabel addLabel(String name, long maxTicks, Runnable task) {
        ProgressLabel progressLabel = new ProgressLabel(name, maxTicks, task);
        list.add(progressLabel);
        root.addComponent(progressLabel.getComponent());
        return progressLabel;
    }

    public void removeLabel(ProgressLabel label) {
        list.remove(label);
        root.removeComponent(label.getComponent());
    }

    public void update() {
        if (list.isEmpty()) return;
        for (ProgressLabel label : list) {
            if (label.getValue() >= label.getMax()) removeLabel(label);
            label.addProgress(1);
        }
    }
}
