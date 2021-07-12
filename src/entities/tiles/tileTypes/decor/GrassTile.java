package entities.tiles.tileTypes.decor;

import entities.tiles.tileTypes.placeable.Placeable;
import enums.GameState;
import enums.TileType;
import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Loader;

public class GrassTile extends DecorTile implements Placeable {
    public GrassTile(TexturedModel model, Vector3f position, float scale, int gridX, int gridY, Loader loader, GameState[] displayOn) {
        super(model, position, scale, gridX, gridY, TileType.GRASS, loader, displayOn);
    }
}
