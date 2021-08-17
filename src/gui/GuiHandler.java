package gui;

import entities.Camera;
import global.Constants;
import gui.components.GuiComponent;
import gui.components.inputs.textInputs.TextInput;
import text.fontRendering.TextMaster;

import java.util.ArrayList;
import java.util.List;

public class GuiHandler implements ComponentHandler {

    protected final GuiRenderer renderer;
    protected final List<GuiComponent> guis = new ArrayList<>();

    public GuiHandler(GuiRenderer renderer) {
        this.renderer = renderer;
    }

    public void addGui(GuiComponent t) {
        guis.add(t);
    }

    public void render(Camera camera) {
        renderer.render(this, camera);
    }

    public void tick() {
        for (GuiComponent component : guis) {
            if (component.getDisplayOn().contains(Constants.gameState)) {
                component.tick();
            } else {
                if (component instanceof TextInput) {
                    if (((TextInput) component).getText() != null) {
                        TextMaster.removeText(((TextInput) component).getText());
                    }
                }
            }
        }
    }

    public GuiComponent getGui(int index) {
        return guis.get(index);
    }

    public void removeGui(GuiComponent component) {
        guis.remove(component);
    }

    public List<GuiComponent> getGuis() {
        return guis;
    }

}
