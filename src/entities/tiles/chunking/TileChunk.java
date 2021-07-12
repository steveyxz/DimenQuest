package entities.tiles.chunking;

import entities.dimensions.Dimension;
import entities.tiles.Tile;
import enums.Biome;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.Arrays;

public class TileChunk {

    public static final TileChunk EMPTY = new TileChunk(Biome.GENERIC, new Vector2f(0, 0));
    private final ArrayList<Tile> tiles;
    private final Biome type;
    private boolean loaded = false;
    private Vector2f pos;

    public TileChunk(Biome type, Dimension handler, Vector2f pos) {
        this(type, new ArrayList<>(), handler, pos);
    }

    public TileChunk(Biome type, ArrayList<Tile> tiles, Dimension handler, Vector2f pos) {
        this.tiles = tiles;
        this.type = type;
        this.pos = pos;
        handler.addChunk(this);
    }

    public TileChunk(Biome b, Vector2f v) {
        this.type = b;
        this.pos = v;
        this.tiles = new ArrayList<>();
    }

    public Vector2f getPos() {
        return pos;
    }

    public void setPos(Vector2f pos) {
        this.pos = pos;
    }

    public Biome getType() {
        return type;
    }

    public void addTile(Tile tile) {
        tiles.add(tile);
    }

    public Tile getTile(int index) {
        return tiles.get(index);
    }

    public void removeTile(Tile tile) {
        tiles.remove(tile);
    }

    public void removeTile(int index) {
        tiles.remove(index);
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public void addGrid(Tile[][] grid) {
        for (Tile[] l : grid) {
            tiles.addAll(Arrays.asList(l));
        }
    }


}
