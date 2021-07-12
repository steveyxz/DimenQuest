package entities.tiles.tileTypes.action;

import entities.tiles.Tile;
import enums.GameState;
import enums.TileType;
import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Loader;

import static global.Constants.tileWidth;

public abstract class ActionTile extends Tile {

    public ActionTile(TexturedModel model, Vector3f position, float scale, int gridX, int gridY, TileType tileType, Loader loader, GameState[] displayOn) {
        super(model, position, scale, gridX, gridY, tileType, loader, displayOn);
    }

    @Override
    public void tick() {
        setPosition(new Vector3f(getGridX() * tileWidth, -getGridY() * tileWidth, -0.2f));
    }

    public abstract void action();

}
