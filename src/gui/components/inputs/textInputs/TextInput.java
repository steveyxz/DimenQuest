package gui.components.inputs.textInputs;

import enums.GameState;
import enums.GuiType;
import gui.GuiHandler;
import gui.GuiTexture;
import gui.components.GuiComponent;
import gui.components.inputs.InputState;
import gui.components.inputs.textInputs.base.InputCursor;
import listeners.MouseListener;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Loader;
import text.fontMeshCreator.FontType;
import text.fontMeshCreator.GUIText;
import text.fontRendering.TextMaster;

import static global.Constants.gameState;

public abstract class TextInput extends GuiComponent {

    protected GuiTexture passiveTexture;
    protected GuiTexture hoverTexture;
    protected GuiTexture selectedTexture;
    protected GuiTexture pointer;
    protected InputCursor cursorObject;
    protected InputState state = InputState.PASSIVE;
    protected GUIText text;
    protected String contents = "";
    protected Loader loader;
    protected String font;
    protected Vector3f textColor = new Vector3f(1, 1, 1);


    public TextInput(GuiTexture texture, GuiTexture pointer, GuiHandler handler, GameState[] display, Loader loader, String font) {
        super(texture, GuiType.TEXT_INPUT, handler, display);
        this.pointer = pointer;
        this.passiveTexture = texture;
        this.hoverTexture = texture;
        this.selectedTexture = texture;
        this.loader = loader;
        this.font = font;
        text = new GUIText(contents, getTexture().getScale().getY() * 20, new FontType(loader.loadTexture(font), "/" + font + ".fnt"), new Vector2f((getTexture().getPos().x + 1) / 2 - (getTexture().getScale().x / 2.2f), (getTexture().getPos().y + 1) / 2 - (getTexture().getScale().y * 1.2f)), getTexture().getScale().x, false);
        text.setColour(textColor.x, textColor.y, textColor.z);
        cursorObject = new InputCursor(pointer, GuiType.POINTER, handler, display, 1);
        cursorObject.setShown(false);
    }

    public TextInput(GuiTexture passiveTexture, GuiTexture pointer, GuiTexture hoverTexture, GuiTexture selectedTexture, GuiHandler handler, GameState[] display, Loader loader, String font) {
        super(passiveTexture, GuiType.TEXT_INPUT, handler, display);
        this.pointer = pointer;
        this.passiveTexture = passiveTexture;
        this.hoverTexture = hoverTexture;
        this.selectedTexture = selectedTexture;
        this.loader = loader;
        this.font = font;
        text = new GUIText(contents, getTexture().getScale().getY() * 20, new FontType(loader.loadTexture(font), "/" + font + ".fnt"), new Vector2f((getTexture().getPos().x + 1) / 2 - (getTexture().getScale().x / 2.2f), (getTexture().getPos().y + 1) / 2 - (getTexture().getScale().y * 1.2f)), getTexture().getScale().x, false);
        text.setColour(textColor.x, textColor.y, textColor.z);
        cursorObject = new InputCursor(pointer, GuiType.POINTER, handler, display, -1);
        cursorObject.setShown(false);
    }

    public Vector3f getTextColor() {
        return textColor;
    }

    public void setTextColor(Vector3f textColor) {
        this.textColor = textColor;
    }

    public InputState getState() {
        return state;
    }

    public void setState(InputState state) {
        this.state = state;
    }

    public GuiTexture getPassiveTexture() {
        return passiveTexture;
    }

    public void setPassiveTexture(GuiTexture passiveTexture) {
        this.passiveTexture = passiveTexture;
    }

    public GuiTexture getHoverTexture() {
        return hoverTexture;
    }

    public void setHoverTexture(GuiTexture hoverTexture) {
        this.hoverTexture = hoverTexture;
    }

    public GuiTexture getSelectedTexture() {
        return selectedTexture;
    }

    public void setSelectedTexture(GuiTexture selectedTexture) {
        this.selectedTexture = selectedTexture;
    }

    public GuiTexture getPointer() {
        return pointer;
    }

    public void setPointer(GuiTexture pointer) {
        this.pointer = pointer;
    }

    public GUIText getText() {
        return text;
    }

    @Override
    public void tick() {
        if (this.getDisplayOn().contains(gameState)) {
            Vector2f pos = getTexture().getPos();
            Vector2f scale = getTexture().getScale();
            if (MouseListener.isMouseClickWithin((int) ((pos.x - (scale.x) + 1) / 2 * Display.getWidth()), (int) ((pos.y - (scale.y) + 1) / 2 * Display.getHeight()), (int) (scale.x * Display.getWidth()), (int) (scale.y * Display.getHeight()))) {
                focus();
                click();
            } else if (MouseListener.checkBounds(Mouse.getX(), Mouse.getY(), (int) ((pos.x - (scale.x) + 1) / 2 * Display.getWidth()), (int) ((pos.y - (scale.y) + 1) / 2 * Display.getHeight()), (int) (scale.x * Display.getWidth()), (int) (scale.y * Display.getHeight()))) {
                if (!(getState() == InputState.FOCUSED)) {
                    setTexture(hoverTexture);
                    setState(InputState.HOVER);
                    hover();
                }
            } else {
                if (Mouse.isButtonDown(0)) {
                    defocus();
                }
                if (getState() == InputState.HOVER) {
                    setState(InputState.PASSIVE);
                    setTexture(passiveTexture);
                }
            }
            if (getState() == InputState.FOCUSED) {
                if (Keyboard.isKeyDown(Keyboard.KEY_BACK)) {
                    if (contents.length() > 0) {
                        contents = contents.substring(0, contents.length() - 1);
                    }
                    updateText();
                }

                if (Keyboard.getEventKeyState()) {
                    if (Keyboard.getEventKey() != Keyboard.CHAR_NONE) {
                        contents += Keyboard.getEventCharacter();
                    }
                }
            }
        } else {
            TextMaster.removeText(text);
            text = null;
        }
    }

    private void updateText() {
        if (text != null) {
            TextMaster.removeText(text);
        }
        text = new GUIText(contents, getTexture().getScale().getY() * 20, new FontType(loader.loadTexture(font), "/" + font + ".fnt"), new Vector2f((getTexture().getPos().x + 1) / 2 - (getTexture().getScale().x / 2.2f), (getTexture().getPos().y + 1) / 2 - (getTexture().getScale().y * 1.2f)), getTexture().getScale().x, false);
        text.setColour(textColor.x, textColor.y, textColor.z);
    }

    public void focus() {
        setTexture(selectedTexture);
        setState(InputState.FOCUSED);
        cursorObject.setShown(true);
        cursorObject.getTexture().setPos(new Vector2f(this.getTexture().getPos().x - this.getTexture().getScale().x + this.cursorObject.getTexture().getScale().x, this.getTexture().getPos().y));
        if (text != null) {
            TextMaster.removeText(text);
        }
        text = new GUIText(contents, getTexture().getScale().getY() * 20, new FontType(loader.loadTexture(font), "/" + font + ".fnt"), new Vector2f((getTexture().getPos().x + 1) / 2 - (getTexture().getScale().x / 2.2f), (getTexture().getPos().y + 1) / 2 - (getTexture().getScale().y * 1.2f)), getTexture().getScale().x, false);
        text.setColour(textColor.x, textColor.y, textColor.z);
    }

    public void defocus() {
        setTexture(passiveTexture);
        setState(InputState.PASSIVE);
        cursorObject.setShown(false);
    }

    public abstract void click();

    public abstract void hover();
}
