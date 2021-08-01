package inventory;

import enums.GameState;
import enums.GuiType;
import gui.GuiHandler;
import gui.GuiTexture;
import gui.components.GuiComponent;
import inventory.items.ItemGui;

public class InventorySlot extends GuiComponent {

    public ItemGui item = null;

    public InventorySlot(GuiTexture texture, GuiType type, GuiHandler handler, GameState[] display) {
        super(texture, type, handler, display);
    }

    public InventorySlot(GuiTexture texture, GuiType type, GuiHandler handler, GameState[] display, int zIndex) {
        super(texture, type, handler, display, zIndex);
    }

    public ItemGui getItem() {
        return item;
    }

    public void setItem(ItemGui item) {
        this.item = item;
    }

    @Override
    public void tick() {
        
    }
}
