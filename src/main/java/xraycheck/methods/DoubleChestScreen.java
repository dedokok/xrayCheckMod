package xraycheck.methods;



import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonClientScreen;

/**
 * This subclass doesn't need to do anything, just be a distinct
 * class so that anyone making edits or adding buttons can find us
 * with an instanceof check
 */
public class DoubleChestScreen extends CottonClientScreen {
    public DoubleChestScreen(GuiDescription description) {
        super(description);
    }
}