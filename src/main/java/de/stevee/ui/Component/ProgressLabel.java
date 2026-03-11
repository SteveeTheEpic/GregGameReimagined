package de.stevee.ui.Component;

import com.googlecode.lanterna.gui2.*;

public class ProgressLabel {
    private final Panel root;
    private final Label nameLabel;
    private final CustomProgressBar bar;

    ProgressLabel(String text, long maxTicks) {
        root = new Panel(new BorderLayout());

        nameLabel = new Label(text);
        bar = new CustomProgressBar(0, maxTicks);
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
