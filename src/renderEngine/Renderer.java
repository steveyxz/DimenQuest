package renderEngine;

import entities.Camera;
import entities.entity.Entity;
import entities.entity.moving.Player;
import entities.tiles.Tile;
import global.Constants;
import global.GlobalMethods;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.shaders.player.PlayerShader;
import renderEngine.shaders.texture.TextureShader;
import tools.maths.TransformationMaths;

import java.util.*;

import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;

public class Renderer {

    public static final float FOV = 70;
    public static final float NEAR_PLANE = 0.1f;
    public static final float FAR_PLANE = 1000;
    private final TextureShader shader;
    private Matrix4f projectionMatrix;

    public Renderer(TextureShader shader, PlayerShader playerShader) {
        this.shader = shader;
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);

    }

    /**
     * Prepares the renderer for another render by clearing the display.
     */
    public void prepare() {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GL11.glClearColor(0, 0, 0, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
    }

    public void render(Map<TexturedModel, List<Entity>> entities, MasterRenderer renderer) {
        Set<TexturedModel> models = entities.keySet();
        List<TexturedModel> realModels = sort(models);
        for (TexturedModel model : realModels) {
            prepareTexturedModel(model);
            List<Entity> batch = entities.get(model);
            for (Entity e : batch) {
                if (e.getDisplayOn().contains(Constants.gameState)) {
                    prepareInstance(e, renderer);
                    GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
                }
            }
            //Render
            unbindTexturedModels();
        }
    }


    private List<TexturedModel> sort(Set<TexturedModel> entities) {
        List<TexturedModel> returned = new ArrayList<>(entities);
        returned.sort(Comparator.comparing(TexturedModel::getGlobalZIndex));
        return returned;
    }

    private void prepareTexturedModel(TexturedModel model) {
        RawModel rawModel = model.getRawModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureID());
    }

    private void unbindTexturedModels() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }

    private void prepareInstance(Entity entity, MasterRenderer renderer) {
        Vector2f pos;
        if (entity instanceof Tile) {
            pos = GlobalMethods.getXYFromGridXY(((Tile) entity).getGridX(), ((Tile) entity).getGridY());
        } else {
            pos = GlobalMethods.getXYFromGridXY(entity.getGridPos().x, entity.getGridPos().y);
        }
        Matrix4f transformationVertex = TransformationMaths.createTransformationMatrix(new Vector3f(pos.x, pos.y, -0.2f),
                entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
        renderer.getShader().changeTransformations(transformationVertex);
    }

    /**
     * Renders a textured model onto the display.
     *
     * @param entity The model to render
     */
    public void renderPlayer(Player entity, PlayerShader shader, Camera camera) {
        TexturedModel model = entity.getModel();
        RawModel rawModel = model.getRawModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);

        Matrix4f transformationVertex = TransformationMaths.createTransformationMatrix(entity.getPosition(),
                entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());

        shader.changeTransformations(transformationVertex);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
        entity.getInventory().render(camera);
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

}
