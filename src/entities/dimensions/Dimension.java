package entities.dimensions;

import classes.Pair;
import entities.Camera;
import entities.entity.Entity;
import entities.tiles.Tile;
import entities.tiles.chunking.ChunkGenerator;
import entities.tiles.chunking.TileChunk;
import enums.Biome;
import enums.DimensionType;
import global.Constants;
import global.GlobalValues;
import org.lwjgl.util.vector.Vector2f;
import renderEngine.MasterRenderer;
import renderEngine.shaders.texture.TextureShader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static global.Constants.chunkHeight;
import static global.Constants.chunkWidth;
import static global.GlobalMethods.decode;

public abstract class Dimension {

    public static boolean isChunksInUse = false;
    private final Camera camera;
    private final TextureShader shader;
    private final MasterRenderer renderer;

    public ArrayList<TileChunk> tileChunks = new ArrayList<>();
    public ArrayList<TileChunk> loadedChunks = new ArrayList<>();

    public ArrayList<Entity> entities = new ArrayList<>();
    public ArrayList<Entity> loadedEntities = new ArrayList<>();

    public DimensionType correspondingType;

    public Vector2f playerPos = new Vector2f(0, 0);

    public Dimension(Camera camera, TextureShader shader, MasterRenderer renderer, DimensionType correspondingType) {
        this.camera = camera;
        this.shader = shader;
        this.renderer = renderer;
        this.correspondingType = correspondingType;
    }

    public static boolean isIsChunksInUse() {
        return isChunksInUse;
    }

    public Vector2f getPlayerPos() {
        return playerPos;
    }

    public void setPlayerPos(Vector2f playerPos) {
        this.playerPos = playerPos;
    }

    public Camera getCamera() {
        return camera;
    }

    public TextureShader getShader() {
        return shader;
    }

    public MasterRenderer getRenderer() {
        return renderer;
    }

    public ArrayList<TileChunk> getTileChunks() {
        return tileChunks;
    }

    public ArrayList<TileChunk> getLoadedChunks() {
        return loadedChunks;
    }

    public void tick() {
        if (!isChunksInUse) {
            isChunksInUse = true;
            for (int i = 0; i < loadedChunks.size(); i++) {
                if (loadedChunks.size() - 1 < i) {
                    break;
                }
                for (int j = 0; j < loadedChunks.get(i).getTiles().size(); j++) {
                    Tile t = loadedChunks.get(i).getTile(j);
                    if (t.getDisplayOn().contains(Constants.gameState)) {
                        t.tick();
                    }
                }
            }
            isChunksInUse = false;
        }
        for (Entity e : loadedEntities) {
            if (e.getDisplayOn().contains(Constants.gameState)) {
                e.tick();
            }
        }
    }

    public void render() {
        renderer.render(camera);
    }

    public void process() {
        if (!isChunksInUse) {
            isChunksInUse = true;
            for (int i = 0; i < loadedChunks.size(); i++) {
                for (int j = 0; j < loadedChunks.get(i).getTiles().size(); j++) {
                    Tile t = loadedChunks.get(i).getTile(j);
                    if (t.getDisplayOn().contains(Constants.gameState)) {
                        renderer.processEntity(t);
                    }
                    if (t.getOnTop() != null) {
                        renderer.processEntity((Entity) t.getOnTop(), 1);
                    }
                }
            }
            isChunksInUse = false;
        }
        for (Entity e : loadedEntities) {
            if (e.getDisplayOn().contains(Constants.gameState)) {
                renderer.processEntity(e);
            }
        }
    }

    public TileChunk getChunkAt(float x, float y) {
        for (TileChunk chunk : tileChunks) {
            if (chunk.getPos().x <= x && chunk.getPos().x + chunkWidth > x && chunk.getPos().y <= y && chunk.getPos().y + chunkHeight > y) {
                return chunk;
            }
        }
        if (GlobalValues.specifiedChunkTypes.containsKey(correspondingType)) {
            for (Long l : GlobalValues.specifiedChunkTypes.get(this.correspondingType).keySet()) {
                if (decode(l).x <= x && decode(l).x + chunkWidth > x && decode(l).y <= y && decode(l).y + chunkHeight > y) {
                    return TileChunk.EMPTY;
                }
            }
        }
        return null;
    }

    public void addChunk(TileChunk entity) {
        tileChunks.add(entity);
    }

    public void removeChunk(TileChunk entity) {
        tileChunks.remove(entity);
    }

    public void clearChunks() {
        tileChunks.clear();
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public void clearEntities() {
        entities.clear();
        loadedEntities.clear();
    }

    public void loadEntity(Entity entity) {
        if (entities.contains(entity)) {
            loadedEntities.add(entity);
        }
    }

    public void unloadEntity(Entity entity) {
        if (entities.contains(entity)) {
            loadedEntities.remove(entity);
        }
    }

    public void loadDimension(ArrayList<TileChunk> load, ArrayList<TileChunk> unload, ArrayList<Pair<Integer>> createChunks, ArrayList<ArrayList<Entity>> loadEntities, ChunkGenerator generator) {
        for (TileChunk c : tileChunks) {
            unloadChunk(c);
        }
        for (TileChunk c : load) {
            loadChunk(c);
        }
        for (TileChunk c : unload) {
            unloadChunk(c);
        }
        for (Entity e : loadEntities.get(0)) {
            loadEntity(e);
        }
        for (Entity e : loadEntities.get(1)) {
            unloadEntity(e);
        }
        createChunks(generator, createChunks);
    }

    public void loadDimension(int x, int y, int xRadius, int yRadius, ChunkGenerator generator) {
        xRadius *= chunkWidth;
        yRadius *= chunkHeight;
        ArrayList<TileChunk> loadedChunkBack = (ArrayList<TileChunk>) loadedChunks.clone();
        int lowestX = x - xRadius;
        int highestX = x + xRadius;
        int highestY = y + yRadius;
        int lowestY = y - yRadius;
        ArrayList<Pair<Integer>> chunksToCreate1 = new ArrayList<>();
        for (int i = y - yRadius; i < y + yRadius + 1; i++) {
            for (int j = x - xRadius; j < x + xRadius + 1; j++) {
                if (j % chunkWidth == 0 && i % chunkHeight == 0) {
                    chunksToCreate1.add(new Pair<>(j, i));
                }
            }
        }
        for (TileChunk c : tileChunks) {
            unloadChunk(c);
        }
        for (TileChunk chunk : tileChunks) {
            if (chunksToCreate1.contains(new Pair<>((int) chunk.getPos().x, (int) chunk.getPos().y))) {
                if (!chunk.isLoaded()) {
                    loadChunk(chunk);
                }
                chunksToCreate1.removeAll(List.of(new Pair<>((int) chunk.getPos().x, (int) chunk.getPos().y)));
            }
        }
        if (loadedChunkBack.equals(loadedChunks)) {
            loadedEntities.clear();
            for (Entity e : entities) {
                if (e.getGridPos().x > lowestX && e.getGridPos().y > lowestY && e.getGridPos().x < highestX && e.getGridPos().y < highestY) {
                    loadEntity(e);
                } else {
                    unloadEntity(e);
                }
            }
        }
        createChunks(generator, chunksToCreate1);
    }

    /**
     * Gets the things to load for player chunks and entities
     *
     * @param x         The x to load from
     * @param y         The y to load from
     * @param xRadius   The radius on the x axis to load to
     * @param yRadius   The radius on the y axis to load to
     * @param generator The chunk generator
     * @return A HashMap in this format: HashMap<HashMap<(Array of created chunks), ArrayList<(Entities to load), (Entities to unload)>>, ArrayList<(Array of loaded chunks), (Array of unloaded chunks)>>
     */
    public HashMap<HashMap<ArrayList<Pair<Integer>>, ArrayList<ArrayList<Entity>>>, ArrayList<ArrayList<TileChunk>>> getLoads(int x, int y, int xRadius, int yRadius, ChunkGenerator generator) {
        ArrayList<TileChunk> tileChunks = (ArrayList<TileChunk>) this.tileChunks.clone();
        xRadius *= 5;
        yRadius *= 5;
        ArrayList<TileChunk> loadedChunkBack = (ArrayList<TileChunk>) loadedChunks.clone();
        int lowestX = x - xRadius;
        int highestX = x + xRadius;
        int highestY = y + yRadius;
        int lowestY = y - yRadius;
        HashMap<HashMap<ArrayList<Pair<Integer>>, ArrayList<ArrayList<Entity>>>, ArrayList<ArrayList<TileChunk>>> returned = new HashMap<>();
        ArrayList<ArrayList<TileChunk>> loadUnload = new ArrayList<>();
        ArrayList<TileChunk> load = new ArrayList<>();
        ArrayList<Entity> loadEntities = new ArrayList<>();
        ArrayList<Entity> unloadEntities = new ArrayList<>();
        ArrayList<ArrayList<Entity>> entityInfo = new ArrayList<>();
        HashMap<ArrayList<Pair<Integer>>, ArrayList<ArrayList<Entity>>> part = new HashMap<>();
        ArrayList<Pair<Integer>> chunksToCreate1 = new ArrayList<>();
        for (int i = y - yRadius; i < y + yRadius + 1; i++) {
            for (int j = x - xRadius; j < x + xRadius + 1; j++) {
                if (j % chunkWidth == 0 && i % chunkHeight == 0) {
                    chunksToCreate1.add(new Pair<>(j, i));
                }
            }
        }
        ArrayList<TileChunk> unload = new ArrayList<>();
        for (TileChunk chunk : tileChunks) {
            if (chunksToCreate1.contains(new Pair<>((int) chunk.getPos().x, (int) chunk.getPos().y))) {
                load.add(chunk);
                chunksToCreate1.removeAll(List.of(new Pair<>((int) chunk.getPos().x, (int) chunk.getPos().y)));
            }
        }
        if (loadedChunkBack.equals(loadedChunks)) {
            loadedEntities.clear();
            for (Entity e : entities) {
                if (e.getGridPos().x > lowestX && e.getGridPos().y > lowestY && e.getGridPos().x < highestX && e.getGridPos().y < highestY) {
                    loadEntities.add(e);
                } else {
                    unloadEntities.add(e);
                }
            }
        }
        loadUnload.add(load);
        loadUnload.add(unload);
        entityInfo.add(loadEntities);
        entityInfo.add(unloadEntities);
        part.put(chunksToCreate1, entityInfo);
        returned.put(part, loadUnload);
        return returned;
    }

    public void unloadChunks(int x, int y, int radius) {
        for (TileChunk chunk : tileChunks) {
            Vector2f chunkPos = chunk.getPos();
            if ((chunkPos.x) + 2 > x - radius - 1 && (chunkPos.x) + 2 < x + radius + 1 && (chunkPos.y) + 2 > y - radius - 1 && (chunkPos.y) + 2 < y + radius + 1) {
                unloadChunk(chunk);
            } else {
                loadChunk(chunk);
            }
        }
    }

    public void loadChunk(TileChunk chunk) {
        if (!chunk.isLoaded()) {
            chunk.setLoaded(true);
            loadedChunks.add(chunk);
        }
    }

    public void unloadChunk(TileChunk chunk) {
        if (chunk.isLoaded()) {
            chunk.setLoaded(false);
            loadedChunks.remove(chunk);
        }
    }

    public ArrayList<TileChunk> getTileChunks(Biome type) {
        ArrayList<TileChunk> returned = new ArrayList<>();
        for (TileChunk e : tileChunks) {
            if (e.getType() == type) {
                returned.add(e);
            }
        }
        return returned;
    }

    public void removeChunks(Biome type) {
        ArrayList<TileChunk> removed = new ArrayList<>();
        for (TileChunk e : tileChunks) {
            if (e.getType() == type) {
                removed.add(e);
            }
        }
        for (TileChunk e : removed) {
            tileChunks.remove(e);
        }
    }

    public abstract void load();

    public abstract void unload();

    public abstract void createChunks(ChunkGenerator generator, ArrayList<Pair<Integer>> info);
}
