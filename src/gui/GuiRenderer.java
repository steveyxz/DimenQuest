package gui;

import global.Constants;
import gui.components.GuiComponent;
import models.RawModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import renderEngine.Loader;
import renderEngine.shaders.gui.GuiShader;
import tools.maths.TransformationMaths;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GuiRenderer {

    private final RawModel quad;
    private final GuiShader shader;

    public GuiRenderer(Loader loader) {
        float[] position = new float[]{-1, 1, -1, -1, 1, 1, 1, -1};
        quad = loader.loadToVAO(position);
        this.shader = new GuiShader();
    }

    public void render(GuiHandler handler) {
        shader.start();
        List<GuiComponent> guis = handler.getGuis();
        List<GuiComponent> renderedGuis = sort(guis);
        GL30.glBindVertexArray(quad.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        for (GuiComponent comp : renderedGuis) {
            if (comp.getDisplayOn().contains(Constants.gameState) && comp.isShown()) {
                GuiTexture t = comp.getTexture();
                GL13.glActiveTexture(GL13.GL_TEXTURE0);
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, t.getTexture());
                Matrix4f matrix = TransformationMaths.createTransformationMatrix(t.getPos(), t.getScale());
                shader.loadTransformation(matrix);
                GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
            }
        }
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        shader.stop();
    }

    private List<GuiComponent> sort(List<GuiComponent> guis) {
        List<GuiComponent> returned = new ArrayList<>();
        for (GuiComponent c : guis) {
            if (c.isShown()) {
                returned.add(c);
            }
        }
        returned.sort(Comparator.comparing(GuiComponent::getZIndex).reversed());
        return returned;
    }

    public void cleanUp() {
        shader.stop();
    }

}
