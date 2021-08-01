package gui.inventory.items;

import enums.GameState;
import enums.GuiType;
import gui.GuiHandler;
import gui.GuiTexture;
import gui.components.GuiComponent;

public abstract class Item extends GuiComponent {

    public ItemState state;

    public Item(GuiTexture texture, GuiType type, GuiHandler handler, GameState[] display, ItemState state) {
        super(texture, type, handler, display);
        this.state = state;
    }

    public Item(GuiTexture texture, GuiType type, GuiHandler handler, GameState[] display, int zIndex, ItemState state) {
        super(texture, type, handler, display, zIndex);
        this.state = state;
    }

    @Override
    public void tick() {
        updateState();
    }

    private void updateState() {
        if (state == ItemState.GROUNDED) {
            setShown(true);
        }
    }
}
