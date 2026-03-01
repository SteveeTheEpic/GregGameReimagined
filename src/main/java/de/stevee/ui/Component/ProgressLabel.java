package de.stevee.ui.Component;

import com.googlecode.lanterna.gui2.*;

public class ProgressLabel {
    private final Panel root;
    private final Label nameLabel;
    private final ProgressBar bar;

    ProgressLabel(String text, int maxTicks) {
        root = new Panel(new BorderLayout());

        nameLabel = new Label(text);
        bar = new ProgressBar(0, maxTicks);
        bar.setPreferredWidth(20); // length of bar, tweak


        root.addComponent(nameLabel, BorderLayout.Location.LEFT);
        root.addComponent(bar, BorderLayout.Location.CENTER);
    }

    Component getComponent() {
        return root;
    }

    void setProgress(int ticks) {
        bar.setValue(ticks);
    }

    void addProgress(int ticks) {
        bar.setValue(bar.getValue() + ticks);
    }
}
