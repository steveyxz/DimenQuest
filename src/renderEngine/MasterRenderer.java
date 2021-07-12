package renderEngine;

import entities.Camera;
import entities.entity.Entity;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import renderEngine.shaders.player.PlayerShader;
import renderEngine.shaders.texture.TextureShader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static renderEngine.Renderer.*;

public class MasterRenderer {

    private final TextureShader shader = new TextureShader();
    private final PlayerShader playerShader = new PlayerShader();
    private final Renderer renderer = new Renderer(shader, playerShader);
    private final HashMap<TexturedModel, List<Entity>> entities = new HashMap<>();
    private Matrix4f projectionMatrix;

    public MasterRenderer() {
        createProjectionMatrix();
        shader.start();
        shader.changeProjections(projectionMatrix);
        shader.stop();
        playerShader.start();
        playerShader.changeProjections(projectionMatrix);
        playerShader.stop();
    }

    public void render(Camera camera) {
        renderer.prepare();
        shader.start();
        shader.changeView(camera);
        renderer.render(entities, this);
        shader.stop();
        entities.clear();
    }

    public Matrix4f getProjectionMatrix() {
        return this.projectionMatrix;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public PlayerShader getPlayerShader() {
        return playerShader;
    }

    public TextureShader getShader() {
        return shader;
    }

    public void processEntity(Entity entity) {
        TexturedModel model = entity.getModel();
        List<Entity> batch = entities.get(model);
        if (batch != null) {
            batch.add(entity);
            entities.put(model, batch);
        } else {
            List<Entity> newBatch = new ArrayList<>();
            newBatch.add(entity);
            entities.put(model, newBatch);
        }
    }

    public void processEntity(Entity entity, int zIndex) {
        TexturedModel model = entity.getModel();
        model.setGlobalZIndex(zIndex);
        List<Entity> batch = entities.get(model);
        if (batch != null) {
            batch.add(entity);
            entities.put(model, batch);
        } else {
            List<Entity> newBatch = new ArrayList<>();
            newBatch.add(entity);
            entities.put(model, newBatch);
        }
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
        shader.cleanUp();
        playerShader.cleanUp();
    }


}
