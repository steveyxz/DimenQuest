package gui;

import entities.Camera;
import global.Constants;
import gui.components.GuiComponent;
import models.RawModel;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;
import renderEngine.Loader;
import renderEngine.shaders.gui.GuiShader;
import tools.maths.TransformationMaths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static renderEngine.Renderer.*;

public class GuiRenderer {

    private final RawModel quad;
    private final GuiShader shader;
    private Matrix4f projectionMatrix;

    public GuiRenderer(Loader loader) {
        this.shader = new GuiShader();
        createProjectionMatrix();
        shader.start();
        shader.stop();
        float[] position = new float[]{-1, 1, -1, -1, 1, 1, 1, -1};
        quad = loader.loadToVAO(position);
    }

    public void render(ComponentHandler handler, Camera camera) {
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

    private void createProjectionMatrix() {
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
    }

    public void cleanUp() {
        shader.stop();
    }

}
