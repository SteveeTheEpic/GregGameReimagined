package de.stevee.Logic;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import de.stevee.Windows.Section;
import de.stevee.API.Render.UI.UI;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Controller {
    public final UI ui;
    public static boolean end = false;

    private final Map<KeyType, Runnable> keyTypeMap = new HashMap<>();
    private final Map<Character, Runnable> keyCharMap = new HashMap<>();

    public Controller() throws IOException {
        this.ui = new UI(this);

        bindKey(KeyType.Escape, () -> {
            if (ui.isSearchGui() && ui.isSearchFocused()) {
                ui.focusResults();
            }
            ui.focusSidebar();
        });

        bindKey(KeyType.PageDown, () -> ui.scrollLog(-3));
        bindKey(KeyType.PageUp, () -> ui.scrollLog(3));

        bindKey(KeyType.Tab, () -> {
            if (ui.isSearchGui()) {
                ui.toggleSearchFocus();
            }
        });

        bindKey(KeyType.Backspace, () -> {
            if (ui.getActiveSection() == Section.MACHINES && ui.getMachinePanel().getMachineInfo().getChildCount() > 0) {
                ui.getMachinePanel().hidePanel();
                return;
            }

            if (!(ui.isSearchGui() && ui.isSearchFocused())) {
                ui.focusSidebar();
            }
        });

        bindKey(KeyType.End, () -> {
            ui.getProcessPanel().addProcess("test252652325223423qwdawsawd24q34rey", 10000, () -> {});
        });

        ui.setActiveSection(Section.FARM);
        ui.logInfo("Welcome!");
    }

    private void bindKey(KeyType key, Runnable task) {
        keyTypeMap.put(key, task);
    }

    private void bindKey(char key, Runnable task) {
        keyCharMap.put(key, task);
    }

    public UI getUI() {
        return ui;
    }

    public void runSelected(Section section) throws IOException {
        if (section == Section.QUIT) {
            end = true;
            return;
        }
        ui.setActiveSection(section);
        ui.logInfo("Section: " + section.title());
    }

    public boolean handleGlobalKey(KeyStroke k) {
        if (k == null) return false;

        if (k.getKeyType() == KeyType.Character && keyCharMap.containsKey(k.getCharacter())) {
            keyCharMap.get(k.getCharacter()).run();
            return true;
        } else if (keyTypeMap.containsKey(k.getKeyType())) {
            keyTypeMap.get(k.getKeyType()).run();
            return true;
        }

        return false;
    }
}
