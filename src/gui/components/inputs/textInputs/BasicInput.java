package gui.components.inputs.textInputs;

import classes.SpriteInfo;
import enums.GameState;
import gui.GuiHandler;
import gui.GuiTexture;
import org.lwjgl.util.vector.Vector2f;
import renderEngine.Loader;

import static global.GlobalMethods.generateGuiTexture;

public class BasicInput extends TextInput {
    public BasicInput(GuiTexture texture, GuiTexture pointer, GuiHandler handler, GameState[] display, Loader loader, String font) {
        super(texture, pointer, handler, display, loader, font);
    }

    public BasicInput(GuiTexture passiveTexture, GuiTexture pointer, GuiTexture hoverTexture, GuiTexture selectedTexture, GuiHandler handler, GameState[] display, Loader loader, String font) {
        super(passiveTexture, pointer, hoverTexture, selectedTexture, handler, display, loader, font);
    }

    public static BasicInput generateBasicInput(String passive, String hover, String selected, String cursor, GuiHandler handler, GameState[] display, Vector2f pos, Vector2f scale, Loader loader, String font) {
        return new BasicInput(generateGuiTexture(passive, loader, pos, scale), generateGuiTexture(cursor, loader, pos, scale), generateGuiTexture(hover, loader, pos, scale), generateGuiTexture(selected, loader, pos, scale), handler, display, loader, font);
    }

    public static BasicInput generateBasicInput(SpriteInfo passive, SpriteInfo hover, SpriteInfo selected, SpriteInfo cursor, GuiHandler handler, GameState[] display, Vector2f pos, Vector2f scale, Vector2f cursorPos, Vector2f cursorScale, Loader loader, String font) {
        return new BasicInput(generateGuiTexture(passive.getSpriteSheet(), passive.getX(), passive.getY(), passive.getWidth(), passive.getHeight(), passive.getGap(), pos, scale), generateGuiTexture(cursor.getSpriteSheet(), cursor.getX(), cursor.getY(), cursor.getWidth(), cursor.getHeight(), cursor.getGap(), cursorPos, cursorScale), generateGuiTexture(hover.getSpriteSheet(), hover.getX(), hover.getY(), hover.getWidth(), hover.getHeight(), hover.getGap(), pos, scale), generateGuiTexture(selected.getSpriteSheet(), selected.getX(), selected.getY(), selected.getWidth(), selected.getHeight(), selected.getGap(), pos, scale), handler, display, loader, font);
    }

    @Override
    public void click() {

    }

    @Override
    public void hover() {

    }
}
