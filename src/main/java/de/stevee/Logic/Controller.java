package de.stevee.Logic;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import de.stevee.Windows.Section;
import de.stevee.ui.UI;

public class Controller {
    public final UI ui;

    private boolean end = false;

    public Controller() {
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

    public void runSelected(Section section) {
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

        if (k.getKeyType() == KeyType.Character) {
            if (!(ui.getActiveSection() == Section.CRAFT && ui.isCraftSearchFocused())) {
                char c = Character.toLowerCase(k.getCharacter());
                if (c == 'a') {
                    ui.focusSidebar();
                    return true;
                }
                if (c == 'd') {
                    ui.focusMain();
                    return true;
                }
            }
        }

        if (k.getKeyType() == KeyType.Escape) {
            if (ui.getActiveSection() == Section.CRAFT && ui.isCraftSearchFocused()) {
                ui.focusCraftResults();
                return true;
            }
            ui.focusSidebar();
            return true;
        }

        if (k.getKeyType() == KeyType.Tab) {
            if (ui.getActiveSection() == Section.CRAFT) {
                ui.toggleCraftFocus();
                return true;
            }
        }

        if (k.getKeyType() == KeyType.Backspace) {
            if (ui.getActiveSection() == Section.CRAFT && ui.isCraftSearchFocused()) {
                return false;
            }
            ui.focusSidebar();
            return true;
        }

        return false;
    }
}
