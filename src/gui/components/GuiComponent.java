package gui.components;

import enums.GameState;
import enums.GuiType;
import gui.GuiHandler;
import gui.GuiTexture;
import inventory.InventorySlot;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class GuiComponent implements Comparable<GuiComponent> {

    private final GuiType type;
    private final GuiHandler handler;
    private final ArrayList<GameState> displayOn = new ArrayList<>();
    private GuiTexture texture;
    private boolean shown = true;
    //Higher z index would bring the element above lower z index
    private int zIndex;

    private boolean stationary = true;

    public GuiComponent(GuiTexture texture, GuiType type, GuiHandler handler, GameState[] display) {
        this(texture, type, handler, display, 0);
    }

    public GuiComponent(GuiTexture texture, GuiType type, GuiHandler handler, GameState[] display, int zIndex) {
        this.texture = texture;
        this.type = type;
        this.handler = handler;
        this.zIndex = zIndex;
        displayOn.addAll(Arrays.asList(display));
        if (!(this instanceof InventorySlot)) {
            handler.addGui(this);
        }
    }

    public boolean isStationary() {
        return stationary;
    }

    public void setStationary(boolean stationary) {
        this.stationary = stationary;
    }

    public int getZIndex() {
        return zIndex;
    }

    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    public boolean isShown() {
        return shown;
    }

    public void setShown(boolean shown) {
        this.shown = shown;
    }

    public ArrayList<GameState> getDisplayOn() {
        return displayOn;
    }

    public abstract void tick();

    public GuiHandler getHandler() {
        return handler;
    }

    public GuiTexture getTexture() {
        return texture;
    }

    public void setTexture(GuiTexture texture) {
        this.texture = texture;
    }

    public GuiType getType() {
        return type;
    }

    @Override
    public int compareTo(GuiComponent o) {
        return Integer.compare(getZIndex(), o.getZIndex());
    }
}
