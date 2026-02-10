package de.stevee.Windows;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.input.KeyStroke;
import de.stevee.Logic.Controller;

import java.util.List;

public class GameWindow extends BasicWindow {
    private final Controller controller;

    public GameWindow(Controller controller) {
        this.controller = controller;

        setHints(List.of(Hint.FULL_SCREEN, Hint.NO_DECORATIONS));
        setComponent(controller.getUI().getRoot());
    }

    @Override
    public boolean handleInput(KeyStroke key) {
        if (controller.handleGlobalKey(key)) {
            return true;
        }

        return super.handleInput(key);
    }
}
