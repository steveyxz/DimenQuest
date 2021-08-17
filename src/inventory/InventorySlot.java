package inventory;

import enums.GameState;
import enums.GuiType;
import global.Constants;
import gui.GuiHandler;
import gui.GuiTexture;
import gui.components.GuiComponent;
import inventory.items.ItemGui;

public class InventorySlot extends GuiComponent {

    public ItemGui item = null;

    public int inventoryX = 0;
    public int inventoryY = 0;

    public boolean active = false;

    private final GuiTexture activeTexture = new GuiTexture(Constants.inventoryActiveTexture);
    private final GuiTexture defaultTexture = new GuiTexture(Constants.inventoryPassiveTexture);

    private boolean isQuickbar = false;

    public InventorySlot(GuiType type, GuiHandler handler, GameState[] display) {
        super(new GuiTexture(Constants.inventoryPassiveTexture), type, handler, display);
    }

    public InventorySlot(GuiType type, GuiHandler handler, GameState[] display, int zIndex) {
        super(new GuiTexture(Constants.inventoryPassiveTexture), type, handler, display, zIndex);
    }

    public InventorySlot(GuiType type, GuiHandler handler, GameState[] display, int zIndex, int invX, int invY) {
        super(new GuiTexture(Constants.inventoryPassiveTexture), type, handler, display, zIndex);
        this.inventoryX = invX;
        this.inventoryY = invY;
        handler.addGui(this);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public GuiTexture getDefaultTexture() {
        return defaultTexture;
    }

    public boolean isQuickbar() {
        return isQuickbar;
    }

    public void setQuickbar(boolean quickbar) {
        isQuickbar = quickbar;
    }

    public int getInventoryX() {
        return inventoryX;
    }

    public void setInventoryX(int inventoryX) {
        this.inventoryX = inventoryX;
    }

    public GuiTexture getActiveTexture() {
        return activeTexture;
    }

    public int getInventoryY() {
        return inventoryY;
    }

    public void setInventoryY(int inventoryY) {
        this.inventoryY = inventoryY;
    }

    public ItemGui getItem() {
        return item;
    }

    public void setItem(ItemGui item) {
        this.item = item;
    }

    @Override
    public void tick() {
        if (active) {
            this.setTexture(activeTexture);
        } else {
            this.setTexture(defaultTexture);
        }
    }
}
