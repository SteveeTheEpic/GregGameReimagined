package de.stevee.Logic;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import de.stevee.Windows.Section;
import de.stevee.ui.UI;

import java.io.IOException;

public class Controller {
    public final UI ui;

    private boolean end = false;

    public Controller() throws IOException {
        this.ui = new UI(this);

        ui.setActiveSection(Section.FARM);
        ui.logInfo("Welcome!");
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public boolean isEnd() {
        return end;
    }

    public UI getUI() {
        return ui;
    }

    public void runSelected(Section section) throws IOException {
        if (section == Section.QUIT) {
            setEnd(true);
            return;
        }
        ui.setActiveSection(section);
        ui.logInfo("Section: " + section.title());
    }

    public boolean handleGlobalKey(KeyStroke k) {
        if (k == null) return false;

        if (k.getKeyType() == KeyType.PageUp) {
            ui.scrollLog(+3);
            return true;
        }
        if (k.getKeyType() == KeyType.PageDown) {
            ui.scrollLog(-3);
            return true;
        }

        if (k.getKeyType() == KeyType.Escape) {
            if (ui.isSearchGui() && ui.isSearchFocused()) {
                ui.focusResults();
                return true;
            }
            ui.focusSidebar();
            return true;
        }

        if (k.getKeyType() == KeyType.Tab) {
            if (ui.isSearchGui()) {
                ui.toggleSearchFocus();
                return true;
            }

            return true;
        }

        if (k.getKeyType() == KeyType.Backspace) {
            if (ui.isSearchGui() && ui.isSearchFocused()) {
                return false;
            }
            ui.focusSidebar();
            return true;
        }

        if (k.getKeyType() == KeyType.End) {
            ui.getProcessPanel().addProcess("test", 1000, () -> {});
        }

        return false;
    }
}
