package gui.components;

import enums.GameState;
import enums.GuiType;
import gui.GuiHandler;
import gui.GuiTexture;

public class StaticImage extends GuiComponent {
    public StaticImage(GuiTexture texture, GuiType type, GuiHandler handler, GameState[] display) {
        super(texture, type, handler, display);
    }

    @Override
    public void tick() {

    }
}
