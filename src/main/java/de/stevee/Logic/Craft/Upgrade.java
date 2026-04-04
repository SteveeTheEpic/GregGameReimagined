package de.stevee.Logic.Craft;



import de.stevee.Logic.Items.Item;
import de.stevee.Logic.Items.Tool;
import de.stevee.Ui.UI;

public class Upgrade extends Craft{
    private final UI ui;
    public Tool product;
    public int tier;

    public Upgrade(String id) {
        super(id);
        ui = UI.getINSTANCE();
    }

    @Override
    public void craft() {
        ingredients.forEach((item, quantity) -> {
            // Checks if the Tool is Upgradeable and the Machine is available
            if ((item.quantity - quantity) >= 0 && machine) {
                item.subQuantity(quantity);
            } else if ((item.quantity - quantity) < 0){
                ui.logInfo("Insufficient " + item.name);
                refund = true;
            } else if (!machine) {
                ui.logInfo(required.getName() + " is required!");
            }
        });

        if (refund || !machine) {
            // refunds every item used in the recipe if the upgrade is refunded
            refundAll();
        } else {
            // adds an tier
            product.setTier(tier);
            ui.logInfo("Upgraded %s to Tier %d".formatted(product.name, product.tier));
        }
    }

    @Override
    public Craft addOutput(Item item, int tier) {
        product = (Tool) item;
        this.tier = tier;
        return this;
    }
}
