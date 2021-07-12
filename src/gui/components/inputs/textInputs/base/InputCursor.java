package gui.components.inputs.textInputs.base;

import enums.GameState;
import enums.GuiType;
import gui.GuiHandler;
import gui.GuiTexture;
import gui.components.GuiComponent;

public class InputCursor extends GuiComponent {

    public InputCursor(GuiTexture texture, GuiType type, GuiHandler handler, GameState[] display) {
        super(texture, type, handler, display);
    }

    public InputCursor(GuiTexture texture, GuiType type, GuiHandler handler, GameState[] display, int zIndex) {
        super(texture, type, handler, display, zIndex);
    }

    @Override
    public void tick() {

    }
}
