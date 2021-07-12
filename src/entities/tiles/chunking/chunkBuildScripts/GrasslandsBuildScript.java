package entities.tiles.chunking.chunkBuildScripts;

import entities.dimensions.Dimension;
import entities.tiles.Tile;
import entities.tiles.tileTypes.decor.DirtTile;
import entities.tiles.tileTypes.decor.GrassTile;
import enums.Biome;
import enums.GameState;
import enums.TileType;
import generation.TileGenerator;
import global.GlobalMethods;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Loader;

import java.util.HashMap;
import java.util.Random;

import static global.Constants.*;

public class GrasslandsBuildScript extends ChunkBuildScript {

    private final Loader loader;

    public GrasslandsBuildScript(Loader loader) {
        this.loader = loader;
    }

    @Override
    public Tile[][] getChunk(Vector2f gridPos, Dimension d) {
        Tile[][] grid = new Tile[chunkHeight][chunkWidth];
        generateBase(grid, gridPos, d);
        return grid;
    }

    private void generateBase(Tile[][] grid, Vector2f gridPos, Dimension d) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                Random r = new Random();
                int rng = r.nextInt(2);
                int x = (int) ((gridPos.x + j) * tileWidth);
                int y = (int) ((-(gridPos.y + i)) * tileWidth);
                grid[i][j] = new DirtTile(dirtModel, new Vector3f(x, y, 0.2f), 1, (int) (gridPos.x + j), (int) gridPos.y + i, loader, new GameState[]{GameState.PLAY});
                if (rng == 0) {
                    grid[i][j].setOnTop(new GrassTile(grassModel, new Vector3f(x, y, 0.2f), 1, (int) (gridPos.x + j), (int) gridPos.y + i, loader, new GameState[]{GameState.PLAY}));
                }
            }
        }
        HashMap<Long, TileType> blend = GlobalMethods.getBlend(Biome.GRASSLANDS, gridPos, d);

        for (Long l : blend.keySet()) {
            Vector2f pos = GlobalMethods.decode(l);
            TileType type = blend.get(l);
            if (type == null) {
                continue;
            }
            Vector2f a = GlobalMethods.getXYFromGridXY(pos.x, pos.y);
            grid[(int) pos.y][(int) pos.x] = TileGenerator.getTile(type, new Vector3f(a.x, a.y, 0.2f), (int) (gridPos.x + pos.x), (int) (gridPos.y + pos.y), loader);
        }

    }


}
