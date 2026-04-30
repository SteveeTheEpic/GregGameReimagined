package de.stevee.Windows.Panels;

import de.stevee.API.Render.ProgressPanel;

public class ProcessPanel extends ProgressPanel {

    public ProcessPanel(String title) {
        super(title);
    }

    public void update() {
        this.getList().update();
    }
}
