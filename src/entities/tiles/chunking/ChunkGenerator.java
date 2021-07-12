package entities.tiles.chunking;

import entities.dimensions.Dimension;
import entities.tiles.chunking.chunkBuildScripts.DesertBuildScript;
import entities.tiles.chunking.chunkBuildScripts.ForestBuildScript;
import entities.tiles.chunking.chunkBuildScripts.GrasslandsBuildScript;
import enums.Biome;
import org.lwjgl.util.vector.Vector2f;
import renderEngine.Loader;

public class ChunkGenerator {

    private final Loader loader;
    private final GrasslandsBuildScript grasslandsBuildScript;
    private final DesertBuildScript desertBuildScript;
    private final ForestBuildScript forestBuildScript;

    public ChunkGenerator(Loader loader) {
        this.loader = loader;
        this.grasslandsBuildScript = new GrasslandsBuildScript(loader);
        this.desertBuildScript = new DesertBuildScript(loader);
        this.forestBuildScript = new ForestBuildScript(loader);
    }

    public TileChunk generateChunk(Biome type, Vector2f pos, Dimension dimension) {
        switch (type) {
            case GRASSLANDS -> {
                TileChunk tileChunk = new TileChunk(Biome.GRASSLANDS, dimension, pos);
                tileChunk.addGrid(grasslandsBuildScript.getChunk(pos, dimension));
                dimension.addChunk(tileChunk);
                return tileChunk;
            }
            case DESERT -> {
                TileChunk tileChunk = new TileChunk(Biome.DESERT, dimension, pos);
                tileChunk.addGrid(desertBuildScript.getChunk(pos, dimension));
                dimension.addChunk(tileChunk);
                return tileChunk;
            }
            case FOREST -> {
                TileChunk tileChunk = new TileChunk(Biome.FOREST, dimension, pos);
                tileChunk.addGrid(forestBuildScript.getChunk(pos, dimension));
                dimension.addChunk(tileChunk);
                return tileChunk;
            }
        }
        return null;
    }

}
