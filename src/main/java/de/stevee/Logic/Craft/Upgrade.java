package de.stevee.Logic.Craft;



import de.stevee.Logic.Items.Item;
import de.stevee.Logic.Items.Tool;
import de.stevee.ui.UI;

import java.util.ArrayList;

import static de.stevee.Utils.Crafts.crafts;

public class Upgrade extends Craft{
    private final UI ui;
    public Tool Product;
    public int Tier;

    public Upgrade(String id) {
        super(id);
        ui = UI.getINSTANCE();
    }

    @Override
    public void craft() {
        Ingredients.forEach((item) -> {
            var Ing_c = Ingredients_Count.get(Ingredients.indexOf(item));

            // Checks if the Tool is Upgradeable and the Machine is available
            if ((item.quantity - Ing_c) >= 0 && machine) {
                item.subQuantity(Ing_c);
            } else if ((item.quantity - Ing_c) < 0){
                ui.logInfo("Insufficient " + item.name);
                refund = true;
            } else if (!machine) {
                ui.logInfo(required.getName() + " is required!");
            }
        });

        if (refund || !machine) {
            // refunds every item used in the recipe if the upgrade is refunded
            for (int i = 0; i < Ingredients.size(); i++) {
                Ingredients.get(i).addQuantity(Ingredients_Count.get(i));
            }
        } else {
            // adds an tier
            Product.setTier(Tier);
            ui.logInfo("Upgraded %s to Tier %d".formatted(Product.name, Product.tier));
        }
    }

    @Override
    public Craft addOutput(Item item, int tier) {
        Product = (Tool) item;
        Tier = tier;
        return this;
    }
}
