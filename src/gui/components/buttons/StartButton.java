package gui.components.buttons;

import classes.SpriteInfo;
import enums.GameState;
import enums.GuiType;
import global.Constants;
import gui.GuiHandler;
import gui.GuiTexture;
import org.lwjgl.util.vector.Vector2f;
import renderEngine.Loader;

import static global.GlobalMethods.generateGuiTexture;

public class StartButton extends Button {
    public StartButton(GuiTexture texture, GuiHandler handler, GameState[] display, int delay) {
        super(texture, GuiType.BUTTON, handler, display, delay);
    }

    public StartButton(GuiTexture texturePassive, GuiTexture textureDown, GuiTexture textureHover, GuiHandler handler, GameState[] display, int delay) {
        super(texturePassive, textureDown, textureHover, GuiType.BUTTON, handler, display, delay);
    }

    public static StartButton generateStartButton(String passive, String down, String hover, GuiHandler handler, GameState[] display, int delay, Vector2f pos, Vector2f scale, Loader loader) {
        return new StartButton(generateGuiTexture(passive, loader, pos, scale), generateGuiTexture(down, loader, pos, scale), generateGuiTexture(hover, loader, pos, scale), handler, display, delay);
    }

    public static StartButton generateStartButton(SpriteInfo passive, SpriteInfo down, SpriteInfo hover, GuiHandler handler, GameState[] display, int delay, Vector2f pos, Vector2f scale, Loader loader) {
        return new StartButton(generateGuiTexture(passive.getSpriteSheet(), passive.getX(), passive.getY(), passive.getWidth(), passive.getHeight(), passive.getGap(), pos, scale), generateGuiTexture(down.getSpriteSheet(), down.getX(), down.getY(), down.getWidth(), down.getHeight(), down.getGap(), pos, scale), generateGuiTexture(hover.getSpriteSheet(), hover.getX(), hover.getY(), hover.getWidth(), hover.getHeight(), hover.getGap(), pos, scale), handler, display, delay);
    }

    public static StartButton generateStartButton(String sheet, int sheetGap, int sheetWidth, int sheetHeight, int passiveX, int passiveY, int downX, int downY, int hoverX, int hoverY, GuiHandler handler, GameState[] display, int delay, Vector2f pos, Vector2f scale, Loader loader) {
        return new StartButton(generateGuiTexture(sheet, passiveX, passiveY, sheetWidth, sheetHeight, sheetGap, pos, scale), generateGuiTexture(sheet, downX, downY, sheetWidth, sheetHeight, sheetGap, pos, scale), generateGuiTexture(sheet, hoverX, hoverY, sheetWidth, sheetHeight, sheetGap, pos, scale), handler, display, delay);
    }

    @Override
    public void click() {
        if (Constants.gameState == GameState.START) {
            Constants.gameState = GameState.PLAY;
        }
    }

    @Override
    public void hover() {

    }
}
