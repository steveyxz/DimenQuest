package entities.tiles.chunking.chunkBuildScripts;

import entities.dimensions.Dimension;
import entities.tiles.Tile;
import org.lwjgl.util.vector.Vector2f;

public abstract class ChunkBuildScript {

    public ChunkBuildScript() {
    }

    public abstract Tile[][] getChunk(Vector2f gridPos, Dimension d);
}
