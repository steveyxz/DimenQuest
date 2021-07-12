package generation;

import entities.tiles.Tile;
import entities.tiles.tileTypes.decor.DirtTile;
import entities.tiles.tileTypes.decor.GrassTile;
import entities.tiles.tileTypes.decor.SandTile;
import entities.tiles.tileTypes.placeable.TreeTile;
import enums.GameState;
import enums.TileType;
import global.Constants;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Loader;

import static global.Constants.grassModel;
import static global.Constants.treeModel;

public class TileGenerator {

    public static Tile getTile(TileType type, Vector3f xyz, int gridX, int gridY, Loader loader) {
        switch (type) {
            case DIRT -> {
                return new DirtTile(Constants.dirtModel, xyz, 1, gridX, gridY, loader, new GameState[]{GameState.PLAY});
            }
            case SAND -> {
                return new SandTile(Constants.sandModel, xyz, 1, gridX, gridY, loader, new GameState[]{GameState.PLAY});
            }
            case GRASS -> {
                DirtTile tile = new DirtTile(Constants.dirtModel, xyz, 1, gridX, gridY, loader, new GameState[]{GameState.PLAY});
                tile.setOnTop(new GrassTile(grassModel, xyz, 1, gridX, gridY, loader, new GameState[]{GameState.PLAY}));
                return tile;
            }
            case TREE -> {
                DirtTile tile = new DirtTile(Constants.dirtModel, xyz, 1, gridX, gridY, loader, new GameState[]{GameState.PLAY});
                tile.setOnTop(new TreeTile(treeModel, xyz, 1, gridX, gridY, loader, new GameState[]{GameState.PLAY}));
                return tile;
            }
        }
        return null;
    }

}
