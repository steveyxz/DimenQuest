package entities.tiles.tileTypes.decor;

import enums.GameState;
import enums.TileType;
import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Loader;

public class SandTile extends DecorTile {
    public SandTile(TexturedModel model, Vector3f position, float scale, int gridX, int gridY, Loader loader, GameState[] displayOn) {
        super(model, position, scale, gridX, gridY, TileType.SAND, loader, displayOn);
    }
}
