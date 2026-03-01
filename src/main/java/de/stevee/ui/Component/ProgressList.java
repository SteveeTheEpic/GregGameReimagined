package de.stevee.ui.Component;

import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;

public class ProgressList {
    private final Panel root;
    private static final java.util.List<ProgressLabel> list = new java.util.ArrayList<>();

    public ProgressList() {
        root = new Panel(new LinearLayout(Direction.VERTICAL));
    }

    public Component getComponent() {
        return root;
    }

    public ProgressLabel addLabel(String name, int maxTicks) {
        ProgressLabel progressLabel = new ProgressLabel(name, maxTicks);
        list.add(progressLabel);
        root.addComponent(progressLabel.getComponent());
        return progressLabel;
    }

    public void removeLabel(ProgressLabel label) {
        list.remove(label);
        root.removeComponent(label.getComponent());
    }

    public static void update() {
        for (ProgressLabel label : list) {
            label.addProgress(1);
        }
    }
}
