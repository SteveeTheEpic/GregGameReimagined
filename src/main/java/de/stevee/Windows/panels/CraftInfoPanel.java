package de.stevee.Windows.panels;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import de.stevee.Logic.Craft.Craft;
import de.stevee.Logic.Machine.Machines;
import de.stevee.Windows.panels.basic.InfoPanel;
import de.stevee.ui.Component.ScrollingLabel;

public class CraftInfoPanel implements InfoPanel<Craft> {
    public void setInformation(Craft craft) {
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
        try {
            InfoPanel.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
