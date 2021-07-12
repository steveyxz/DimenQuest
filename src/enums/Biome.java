package enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum Biome {

    GENERIC(0, 0, 0, DimensionType.GENERIC, new TileType[]{}, BiomeDifferType.GENERIC),
    DESERT(0.4f, 4, 15, DimensionType.STARTER_WORLD, new TileType[]{TileType.SAND}, BiomeDifferType.DRY),
    FOREST(0.34f, 6, 12, DimensionType.STARTER_WORLD, new TileType[]{TileType.TREE, TileType.DIRT}, BiomeDifferType.FERTILE),
    GRASSLANDS(0.3f, 10, 8, DimensionType.STARTER_WORLD, new TileType[]{TileType.GRASS, TileType.DIRT}, BiomeDifferType.FERTILE);

    private float spreadChance;
    private int creationChance;
    private DimensionType dimensionIn;
    private int minChunkSize;
    private TileType[] foundInHere;
    private BiomeDifferType differType;

    /**
     * A biome in the game
     *
     * @param spreadChance   Number between 0 and 1 signifying the chance of
     *                       the biome spreading to the surrounding chunks with
     *                       0 being no chance and 1 being always
     * @param creationChance Number being the number of chances that the biome has
     *                       of getting picked, meaning a biome with a creation
     *                       chance of 2 would have twice the chances of one with
     *                       creation chance 1.
     */
    Biome(float spreadChance, int creationChance, int minChunkSize, DimensionType type, TileType[] foundInHere, BiomeDifferType biomeDifferType) {
        this.spreadChance = spreadChance;
        this.creationChance = creationChance;
        this.dimensionIn = type;
        this.minChunkSize = minChunkSize;
        this.foundInHere = foundInHere;
        this.differType = biomeDifferType;
    }

    public BiomeDifferType getDifferType() {
        return differType;
    }

    public void setDifferType(BiomeDifferType differType) {
        this.differType = differType;
    }

    public TileType[] getFoundInHere() {
        return foundInHere;
    }

    public void setFoundInHere(TileType[] foundInHere) {
        this.foundInHere = foundInHere;
    }

    public TileType getRandomTileTypeFromBiome() {
        if (getFoundInHere().length < 1) {
            return null;
        }
        return getFoundInHere()[new Random().nextInt(getFoundInHere().length)];
    }

    public int getMinChunkSize() {
        return minChunkSize;
    }

    public void setMinChunkSize(int minChunkSize) {
        this.minChunkSize = minChunkSize;
    }

    public float getSpreadChance() {
        return spreadChance;
    }

    public void setSpreadChance(float spreadChance) {
        this.spreadChance = spreadChance;
    }

    public int getCreationChance() {
        return creationChance;
    }

    public void setCreationChance(int creationChance) {
        this.creationChance = creationChance;
    }

    public DimensionType getDimensionIn() {
        return dimensionIn;
    }

    public void setDimensionIn(DimensionType dimensionIn) {
        this.dimensionIn = dimensionIn;
    }

    public Biome getRandomType() {
        Biome returned = Biome.values()[new Random().nextInt(Biome.values().length)];
        if (returned == Biome.GENERIC) {
            return getRandomType();
        }
        return returned;
    }

    public int getTotalCreationChance(DimensionType biomeType, Biome... ignore) {
        int total = 0;
        for (Biome b : Biome.values()) {
            if (!Arrays.asList(ignore).contains(b)) {
                total += b.getCreationChance();
            }
        }
        return total;
    }

    public List<Biome> getDimensionBiomes(DimensionType type) {
        List<Biome> returned = new ArrayList<>();
        for (Biome b : Biome.values()) {
            if (b.getDimensionIn() == type) {
                returned.add(b);
            }
        }
        return returned;
    }

}
