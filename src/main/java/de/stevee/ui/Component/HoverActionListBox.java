package de.stevee.ui.Component;

import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.input.KeyStroke;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HoverActionListBox extends ActionListBox {
    List<Listener> listeners = new ArrayList<>();

    @Override
    public Interactable.Result handleKeyStroke(KeyStroke keyStroke) {
        int oldIndex = getSelectedIndex();

        Interactable.Result result = super.handleKeyStroke(keyStroke);
        int newIndex = getSelectedIndex();

        if (newIndex != oldIndex) {
            for (Listener listener : listeners) {
                try {
                    listener.onHoverChanged(newIndex, getItemAt(newIndex));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    public interface Listener {
        void onHoverChanged(int newIndex, Runnable newItem) throws IOException;
    }
}
