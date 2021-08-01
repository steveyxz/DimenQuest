package gui.inventory;

import enums.GameState;
import enums.GuiType;
import gui.GuiHandler;
import gui.GuiTexture;
import gui.components.GuiComponent;
import gui.inventory.items.Item;

public class InventorySlot extends GuiComponent {

    public Item item = null;

    public InventorySlot(GuiTexture texture, GuiType type, GuiHandler handler, GameState[] display) {
        super(texture, type, handler, display);
    }

    public InventorySlot(GuiTexture texture, GuiType type, GuiHandler handler, GameState[] display, int zIndex) {
        super(texture, type, handler, display, zIndex);
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public void tick() {
        
    }
}
