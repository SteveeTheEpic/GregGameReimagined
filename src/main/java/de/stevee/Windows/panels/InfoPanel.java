package de.stevee.Windows.panels;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import de.stevee.Logic.Craft.Craft;
import de.stevee.Logic.Items.Item;
import de.stevee.Logic.Machine.Machines;
import de.stevee.Main;
import de.stevee.Windows.GameWindow;

import java.util.Set;

public class InfoPanel {
    private final BasicWindow infoPanelWindow = new BasicWindow();
    private static final Panel panel = new Panel(new LinearLayout(Direction.VERTICAL));


    public InfoPanel() {
        infoPanelWindow.setHints(Set.of(
                Window.Hint.NO_DECORATIONS
        ));
        infoPanelWindow.setTheme(Main.gui.getTheme());
        infoPanelWindow.setComponent(panel);
        infoPanelWindow.setFixedSize(new TerminalSize(10, Main.gui.getScreen().getTerminalSize().getRows()));
        infoPanelWindow.setPosition(new TerminalPosition(Main.gui.getScreen().getTerminalSize().getColumns() - 10, 0));
        Main.gui.addWindow(infoPanelWindow);
    }

    public static void setInformation(Craft craft) {
        panel.removeAllComponents();
        panel.addComponent(new Label("Craft Info"));
        panel.addComponent(new Label("Recipe: %s".formatted(craft.getId())));
        if (craft.getIngredients().size() > 1)
            panel.addComponent(new Label("Ingredients:"));
        else {
            panel.addComponent(new Label("Ingredient:"));
        }
        for (Item item : craft.getIngredients().keySet()) {
            panel.addComponent(new Label("%s: %d".formatted(item.name, craft.getIngredients().get(item))));
        }

        if (craft.getProducts().size() > 1)
            panel.addComponent(new Label("Products:"));
        else {
            panel.addComponent(new Label("Product:"));
        }
        for (Item item : craft.getProducts().keySet()) {
            panel.addComponent(new Label("%s: %d".formatted(item.name, craft.getProducts().get(item))));
        }

        if (craft.required != Machines.None) {
            panel.addComponent(new Label("Machine: %s".formatted(craft.required.name)));
        }
    }

    public BasicWindow getInfoPanelWindow() {
        return infoPanelWindow;
    }
}
