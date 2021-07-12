package generation;

import entities.tiles.RectQuad;
import enums.EntityType;
import enums.GameState;
import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Loader;
import textures.ModelTexture;

public class ShapeGenerator {

    public static RectQuad getQuadEntity(float width, float height, Vector3f pos, Loader loader, GameState[] display) {
        float[] vertices = {
                0f, 0f, 0f,//v0
                0f, -height, 0f,//v1
                width, -height, 0f,//v2
                width, 0, 0f,//v3
        };

        int[] indices = {
                0, 1, 3,//top left triangle (v0, v1, v3)
                3, 1, 2//bottom right triangle (v3, v1, v2)
        };

        float[] textures = {
                0, 0,
                0, 1,
                1, 1,
                1, 0
        };
        return new RectQuad(new TexturedModel(loader.loadToVAO(vertices, textures, indices), new ModelTexture(0)), pos, 0, 0, 0, 1f, EntityType.GENERIC_RECTANGLE, display);
    }

}
