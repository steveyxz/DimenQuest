package entities.tiles.world;

import entities.dimensions.Dimension;
import entities.tiles.chunking.ChunkGenerator;

public class WorldHandler {

    private final Dimension dimension;
    private final ChunkGenerator generator;

    public WorldHandler(Dimension dimension, ChunkGenerator generator) {
        this.dimension = dimension;
        this.generator = generator;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public ChunkGenerator getGenerator() {
        return generator;
    }
}
