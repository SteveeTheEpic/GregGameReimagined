package de.stevee.Windows.panels;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import de.stevee.Logic.Craft.Craft;
import de.stevee.Logic.Machine.Machines;
import de.stevee.Main;
import de.stevee.ui.Component.ScrollingLabel;

import java.io.IOException;
import java.util.Set;

public class InfoPanel {
    private static final BasicWindow infoPanelWindow = new BasicWindow();
    private static final Panel panel = new Panel(new LinearLayout(Direction.VERTICAL));

    public static void init(WindowBasedTextGUI gui) {
        infoPanelWindow.setHints(Set.of(Window.Hint.NO_DECORATIONS, Window.Hint.NO_FOCUS));
        infoPanelWindow.setTheme(gui.getTheme());

        infoPanelWindow.setComponent(panel.withBorder(Borders.singleLine()));

        // Add to GUI once
        gui.addWindow(infoPanelWindow);
    }

    public static void setInformation(Craft craft) throws IOException {
        panel.removeAllComponents();

        panel.addComponent(new Label("Craft Info"));
        panel.addComponent(new ScrollingLabel("%s".formatted(craft.getId()), panel.getSize().getColumns()));

        // Ingredients
        String ingLabel = craft.getIngredients().size() > 1 ? "Ingredients:" : "Ingredient:";
        panel.addComponent(new Label(ingLabel));
        for (var entry : craft.getIngredients().entrySet()) {
            Label label = new Label("%s: %d".formatted(entry.getKey().name, entry.getValue()));
            if (entry.getKey().quantity >= entry.getValue()) label.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
            else label.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
            panel.addComponent(label);
        }

        // Products
        String prodLabel = craft.getProducts().size() > 1 ? "Products:" : "Product:";
        panel.addComponent(new Label(prodLabel));
        for (var entry : craft.getProducts().entrySet()) {
            panel.addComponent(new Label("%s: %d".formatted(entry.getKey().name, entry.getValue())));
        }

        if (craft.required != Machines.None) {
            Label label = new Label("Machine: %s".formatted(craft.required.name));
            if (craft.required.quantity > 0) label.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
            else label.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
            panel.addComponent(label);
        }

        // Force repaint
        update();
    }

    public static void updatePosition() {
        TerminalSize screenSize = Main.gui.getScreen().getTerminalSize();
        TerminalSize panelSize = new TerminalSize(25, screenSize.getRows() - 2);

        infoPanelWindow.setFixedSize(panelSize);
        infoPanelWindow.setPosition(new TerminalPosition(
                screenSize.getColumns() - panelSize.getColumns(),  // RIGHT EDGE
                1  // Small top margin
        ));
    }

    public static void update() throws IOException {
        infoPanelWindow.invalidate();
        infoPanelWindow.getTextGUI().updateScreen();
    }

    public static void setVisibility(boolean visible) throws IOException {
        panel.setVisible(visible);
        infoPanelWindow.setVisible(visible);
        update();
    }

    public static BasicWindow getInfoPanelWindow() {
        return infoPanelWindow;
    }
}
