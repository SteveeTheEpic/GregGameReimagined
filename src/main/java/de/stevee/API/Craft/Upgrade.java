package de.stevee.API.Craft;



import de.stevee.API.Items.Item;
import de.stevee.API.Items.Tool;
import de.stevee.API.Render.UI.UI;

public class Upgrade extends Craft{
    private final UI ui = UI.getINSTANCE();
    public Tool product;
    public int tier;

    public Upgrade(String id) {
        super(id);
    }

    @Override
    public void craft() {
        ingredients.forEach((item, quantity) -> {
            // Checks if the Tool is Upgradeable and the Machine is available
            if ((item.quantity - quantity) >= 0 && hasMachine) {
                item.subQuantity(quantity);
            } else if ((item.quantity - quantity) < 0){
                ui.logInfo("Insufficient " + item.name);
                refund = true;
            } else if (!hasMachine) {
                ui.logInfo(requiredMachine.getName() + " is required!");
            }
        });

        if (refund || !hasMachine) {
            refundAll();
        } else {
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
