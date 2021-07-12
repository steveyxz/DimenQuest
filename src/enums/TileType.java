package enums;

import java.util.Arrays;
import java.util.Random;

public enum TileType {

    GENERIC(new Biome[]{}),
    DIRT(new Biome[]{Biome.GRASSLANDS, Biome.FOREST}),
    SAND(new Biome[]{Biome.DESERT}),
    TREE(new Biome[]{Biome.FOREST, Biome.GRASSLANDS}),
    GRASS(new Biome[]{Biome.GRASSLANDS});

    private Biome[] compatible;

    TileType(Biome[] compatible) {
        this.compatible = compatible;
    }

    public Biome[] getCompatible() {
        return compatible;
    }

    public void setCompatible(Biome[] compatible) {
        this.compatible = compatible;
    }

    public TileType getRandomType() {
        return TileType.values()[new Random().nextInt(TileType.values().length)];
    }

    public TileType getRandomType(Biome compatibleWith) {
        TileType type = TileType.values()[new Random().nextInt(TileType.values().length)];
        if (Arrays.asList(type.getCompatible()).contains(compatibleWith) && type != TileType.GENERIC) {
            return TileType.values()[new Random().nextInt(TileType.values().length)];
        }
        return getRandomType(compatibleWith);
    }

}
