package de.stevee.Windows.panels;

import com.googlecode.lanterna.gui2.*;
import de.stevee.Windows.panels.basic.ProgressPanel;
import de.stevee.ui.Component.ProgressList;

public class ProcessPanel extends ProgressPanel {

    public ProcessPanel(String title) {
        super(title);
    }

    public void update() {
        this.getList().update();
    }
}
