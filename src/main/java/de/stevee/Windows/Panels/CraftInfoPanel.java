package de.stevee.Windows.Panels;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import de.stevee.API.Craft.Craft;
import de.stevee.API.Machine.Machines;
import de.stevee.API.Render.InfoPanel;
import de.stevee.API.Render.UI.Component.ScrollingLabel;

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

        if (craft.requiredMachine != Machines.NONE) {
            Label label = new Label("Machine: %s".formatted(craft.requiredMachine.name));
            if (craft.requiredMachine.quantity > 0) label.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
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
