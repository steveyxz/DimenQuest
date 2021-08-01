package entities.tiles;

import entities.entity.Entity;
import entities.tiles.chunking.TileChunk;
import entities.tiles.tileTypes.placeable.Placeable;
import enums.EntityType;
import enums.GameState;
import enums.TileType;
import global.Constants;
import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Loader;
import renderEngine.spriteHandler.SpriteGlobal;
import textures.ModelTexture;

import static global.Constants.tileWidth;

public abstract class Tile extends Entity {

    protected int gridX;
    protected int gridY;
    protected TileChunk parent;
    protected Loader loader;
    protected TileType tileType;
    protected Placeable onTop = null;

    public Tile(TexturedModel model, Vector3f position, float scale, int gridX, int gridY, TileType tileType, Loader loader, GameState[] displayOn) {
        super(model, position, 0, 0, 0, scale, EntityType.TILE, displayOn);
        this.gridX = gridX;
        this.gridY = gridY;
        this.tileType = tileType;
        this.loader = loader;
    }

    public static TexturedModel generateModel(Loader loader, String texture) {
        float[] vertices = {
                0f, 0f, 0f,//v0
                0f, -tileWidth, 0f,//v1
                tileWidth, -tileWidth, 0f,//v2
                tileWidth, 0, 0f,//v3
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
        return new TexturedModel(loader.loadToVAO(vertices, textures, indices), new ModelTexture(loader.loadTexture(texture)));
    }

    public static TexturedModel generateModel(Loader loader, int x, int y) {
        float[] vertices = {
                0f, 0f, 0f,//v0
                0f, -tileWidth, 0f,//v1
                tileWidth, -tileWidth, 0f,//v2
                tileWidth, 0, 0f,//v3
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
        return new TexturedModel(loader.loadToVAO(vertices, textures, indices), new ModelTexture(SpriteGlobal.loadTexture("tiles", Constants.tileSpriteSheetTextureWidth, Constants.tileSpriteSheetTextureHeight, Constants.tileSpriteSheetGap, x, y)));
    }

    public Placeable getOnTop() {
        return onTop;
    }

    public void setOnTop(Placeable onTop) {
        this.onTop = onTop;
    }

    public boolean hasParent() {
        if (getParent() != null) {
            return null != getParent();
        }
        return false;
    }

    public TileChunk getParent() {
        return parent;
    }

    public void setParent(TileChunk parent) {
        this.parent = parent;
    }

    public int getGridX() {
        return gridX;
    }

    public void setGridX(int gridX) {
        this.gridX = gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public void setGridY(int gridY) {
        this.gridY = gridY;
    }
}
