package entities.tiles.chunking;

import enums.Biome;
import org.lwjgl.util.vector.Vector2f;

public class TileChunkInfo {

    private Vector2f pos;
    private Biome type;

    public TileChunkInfo(Vector2f pos, Biome type) {
        this.pos = pos;
        this.type = type;
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

    public void setType(Biome type) {
        this.type = type;
    }
}
