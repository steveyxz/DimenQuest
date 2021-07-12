package entities.tiles.tileTypes.placeable;

import entities.tiles.tileTypes.action.ActionTile;
import enums.GameState;
import enums.TileType;
import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Loader;

public class TreeTile extends ActionTile implements Placeable, Breakable {
    public TreeTile(TexturedModel model, Vector3f position, float scale, int gridX, int gridY, Loader loader, GameState[] displayOn) {
        super(model, position, scale, gridX, gridY, TileType.TREE, loader, displayOn);
    }

    @Override
    public void action() {

    }
}
