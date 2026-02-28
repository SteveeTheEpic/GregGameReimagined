package de.stevee.Windows.panels;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

public class SidePanel extends ActionListBox {
    private final Label header = new Label("Actions");
    public SidePanel() {
        super();
    }

    @Override
    public Result handleKeyStroke(KeyStroke keyStroke) {
        if (keyStroke == null) return Result.UNHANDLED;

        if (keyStroke.getKeyType() == KeyType.Character) {
            char c = Character.toLowerCase(keyStroke.getCharacter());
            if (c == 'w') {
                return super.handleKeyStroke(new KeyStroke(KeyType.ArrowUp));
            }
            if (c == 's') {
                return super.handleKeyStroke(new KeyStroke(KeyType.ArrowDown));
            }
            if (c == ' ') {
                activateSelected();
                return Interactable.Result.HANDLED;
            }
        }
        return super.handleKeyStroke(keyStroke);
    }


    private void activateSelected() {
        runSelectedItem();
    }
}