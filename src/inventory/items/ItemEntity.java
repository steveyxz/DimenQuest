package inventory.items;

import entities.entity.Entity;
import enums.EntityType;
import enums.GameState;
import global.Constants;
import models.TexturedModel;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Loader;
import renderEngine.spriteHandler.SpriteGlobal;
import textures.ModelTexture;

import static global.Constants.tileWidth;

public class ItemEntity extends Entity {

    private ItemType itemType;

    public ItemEntity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, GameState[] display, ItemType itemType) {
        super(model, position, rotX, rotY, rotZ, 1, EntityType.ITEM, display);
        this.itemType = itemType;
    }

    public ItemEntity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, GameState[] display, Vector2f gridPos, ItemType itemType) {
        super(model, position, rotX, rotY, rotZ, 1, EntityType.ITEM, display, gridPos);
        this.itemType = itemType;
    }

    public static TexturedModel generateModel(Loader loader, int x, int y) {
        float[] vertices = {
                0f, 0f, 0f,//v0
                0f, -tileWidth/2, 0f,//v1
                tileWidth/2, -tileWidth/2, 0f,//v2
                tileWidth/2, 0, 0f,//v3
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
        return new TexturedModel(loader.loadToVAO(vertices, textures, indices), new ModelTexture(SpriteGlobal.loadTexture("items", Constants.itemsSpriteSheetTextureWidth, Constants.itemsSpriteSheetTextureHeight, Constants.itemsSpriteSheetGap, x, y)));
    }
    @Override
    public void tick() {

    }
}
