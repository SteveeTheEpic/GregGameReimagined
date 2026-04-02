package de.stevee.Windows.Panels.Basic;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import de.stevee.Ui.UI;

import java.io.IOException;
import java.util.Set;

public interface InfoPanel<T> {
    BasicWindow infoPanelWindow = new BasicWindow();
    Panel panel = new Panel(new LinearLayout(Direction.VERTICAL));

    static void init(WindowBasedTextGUI gui) {
        infoPanelWindow.setHints(Set.of(Window.Hint.NO_DECORATIONS, Window.Hint.NO_FOCUS));
        infoPanelWindow.setTheme(gui.getTheme());

        infoPanelWindow.setComponent(panel.withBorder(Borders.singleLine()));

        // Add to GUI once
        gui.addWindow(infoPanelWindow);
    }

    default void setInformation(T information) {}

    static void updatePosition() {
        TerminalSize screenSize = UI.gui.getScreen().getTerminalSize();
        TerminalSize panelSize = new TerminalSize(25, screenSize.getRows() - 2);

        infoPanelWindow.setFixedSize(panelSize);
        infoPanelWindow.setPosition(new TerminalPosition(
                screenSize.getColumns() - panelSize.getColumns(),  // RIGHT EDGE
                1  // Small top margin
        ));
    }

     static void update() throws IOException {
        infoPanelWindow.invalidate();
        infoPanelWindow.getTextGUI().updateScreen();
    }

     default void setVisibility(boolean visible) throws IOException {
        panel.setVisible(visible);
        infoPanelWindow.setVisible(visible);
        update();
    }

     static BasicWindow getInfoPanelWindow() {
        return infoPanelWindow;
    }
}
