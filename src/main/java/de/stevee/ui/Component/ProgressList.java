package de.stevee.ui.Component;

import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;

import java.util.ArrayList;
import java.util.List;

public class ProgressList {
    private static Panel root = null;
    private static final List<ProgressLabel> list = new ArrayList<>();

    public ProgressList() {
        root = new Panel(new LinearLayout(Direction.VERTICAL));
    }

    public Component getComponent() {
        return root;
    }

    public ProgressLabel addLabel(String name, long maxTicks) {
        ProgressLabel progressLabel = new ProgressLabel(name, maxTicks);
        list.add(progressLabel);
        root.addComponent(progressLabel.getComponent());
        return progressLabel;
    }

    public static void removeLabel(ProgressLabel label) {
        list.remove(label);
        root.removeComponent(label.getComponent());
    }

    public static void update() {
        for (ProgressLabel label : list) {
            if (label.getValue() == label.getMax()) removeLabel(label);
            label.addProgress(1);
        }
    }
}
