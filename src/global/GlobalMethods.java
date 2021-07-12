package global;

import classes.Pair;
import classes.SpriteInfo;
import entities.dimensions.Dimension;
import entities.tiles.chunking.TileChunk;
import enums.Biome;
import enums.TileType;
import gui.GuiTexture;
import models.TexturedModel;
import org.lwjgl.util.vector.Vector2f;
import renderEngine.Loader;
import renderEngine.spriteHandler.SpriteGlobal;
import textures.ModelTexture;

import java.util.*;

import static global.Constants.*;
import static global.GlobalValues.specifiedChunkTypes;

public class GlobalMethods {

    public static Vector2f getGridXYFromXY(int x, int y) {
        //Grid x will be the x / tileWidth int
        //Eg. x = 0.6, gridX = 0.6 / 0.3 = 2;
        //Eg. x = 0.5, gridX = 0.5 / 0.3 = 1 2/3 int -> 1

        return new Vector2f((float) Math.floor(x / tileWidth), (float) Math.floor(y / tileWidth));
    }

    public static Vector2f getXYFromGridXY(float x, float y) {
        return new Vector2f(x * tileWidth, (y * tileWidth));
    }

    public static GuiTexture generateGuiTexture(String path, Loader loader, Vector2f pos, Vector2f scale) {
        return new GuiTexture(loader.loadTexture(path), pos, scale);
    }

    public static GuiTexture generateGuiTexture(String path, Loader loader, Vector2f pos) {
        return new GuiTexture(loader.loadTexture(path), pos, new Vector2f(1, 1));
    }

    public static GuiTexture generateGuiTexture(String path, int x, int y, int width, int height, int gap, Vector2f pos, Vector2f scale) {
        return new GuiTexture(SpriteGlobal.loadTexture(path, width, height, gap, x, y), pos, scale);
    }

    public static GuiTexture generateGuiTexture(String path, Loader loader) {
        return new GuiTexture(loader.loadTexture(path), new Vector2f(0, 0), new Vector2f(1, 1));
    }

    public static long encode(int x, int y) {
        return (long) x << 32 | y & 0xFFFFFFFFL;
    }

    public static Vector2f decode(long l) {
        int aBack = (int) (l >> 32);
        int bBack = (int) l;
        return new Vector2f(aBack, bBack);
    }

    public static ArrayList<Integer> getAnimationTextures(Loader loader, String... paths) {
        ArrayList<Integer> returned = new ArrayList<>();
        for (String path : paths) {
            returned.add(loader.loadTexture(path));
        }
        return returned;
    }

    public static ArrayList<Integer> getAnimationTextures(String spriteSheet, int width, int height, int gap, Long... infos) {
        ArrayList<Integer> returned = new ArrayList<>();
        for (Long info : infos) {
            Vector2f decoded = GlobalMethods.decode(info);
            returned.add(SpriteGlobal.loadTexture(spriteSheet, width, height, gap, (int) decoded.x, (int) decoded.y));
        }

        return returned;
    }

    public static TexturedModel getSquareModelFromTexture(String texture, Loader loader) {
        return new TexturedModel(loader.loadToVAO(Constants.rectVertices, Constants.rectTextureCoords, Constants.rectIndices), new ModelTexture(loader.loadTexture(texture)));
    }

    public static TexturedModel getSquareModelFromTexture(SpriteInfo info, Loader loader) {
        return new TexturedModel(loader.loadToVAO(Constants.rectVertices, Constants.rectTextureCoords, Constants.rectIndices), new ModelTexture(SpriteGlobal.loadTexture(info.getSpriteSheet(), info.getWidth(), info.getHeight(), info.getGap(), info.getX(), info.getY())));
    }

    public static TexturedModel getSquareModelFromTexture(SpriteInfo info, Loader loader, float width, float height) {
        float[] rectVertices = {
                0f, 0f, 0f,//v0
                0f, -height, 0f,//v1
                width, -height, 0f,//v2
                width, 0, 0f,//v3
        };
        return new TexturedModel(loader.loadToVAO(rectVertices, Constants.rectTextureCoords, Constants.rectIndices), new ModelTexture(SpriteGlobal.loadTexture(info.getSpriteSheet(), info.getWidth(), info.getHeight(), info.getGap(), info.getX(), info.getY())));
    }

    public static Biome getSpreadBiome(Dimension dimension, Long p) {
        if (specifiedChunkTypes.containsKey(dimension.correspondingType)) {
            if (specifiedChunkTypes.get(dimension.correspondingType).containsKey(p)) {
                Biome theBiome = specifiedChunkTypes.get(dimension.correspondingType).get(p);
                specifiedChunkTypes.get(dimension.correspondingType).remove(p);
                return theBiome;
            }
        } else {
            specifiedChunkTypes.put(dimension.correspondingType, new HashMap<>());
        }

        HashMap<Long, Biome> dimensionSpecified = specifiedChunkTypes.get(dimension.correspondingType);

        Random rand = new Random();

        Pair<Integer> chunkGenPos = new Pair<>((int) decode(p).x, (int) decode(p).y);

        long topChunk = encode(chunkGenPos.getA(), chunkGenPos.getB() - chunkHeight);
        long bottomChunk = encode(chunkGenPos.getA(), chunkGenPos.getB() + chunkHeight);
        long leftChunk = encode(chunkGenPos.getA() - chunkWidth, chunkGenPos.getB());
        long rightChunk = encode(chunkGenPos.getA() + chunkWidth, chunkGenPos.getB());
        long topRightChunk = encode(chunkGenPos.getA() + chunkWidth, chunkGenPos.getB() - chunkHeight);
        long bottomRightChunk = encode(chunkGenPos.getA() + chunkWidth, chunkGenPos.getB() + chunkHeight);
        long topLeftChunk = encode(chunkGenPos.getA() - chunkWidth, chunkGenPos.getB() - chunkHeight);
        long bottomLeftChunk = encode(chunkGenPos.getA() - chunkWidth, chunkGenPos.getB() + chunkHeight);

        TileChunk topChunkChunk = dimension.getChunkAt(decode(topChunk).x, decode(topChunk).y);
        TileChunk bottomChunkChunk = dimension.getChunkAt(decode(bottomChunk).x, decode(bottomChunk).y);
        TileChunk leftChunkChunk = dimension.getChunkAt(decode(leftChunk).x, decode(leftChunk).y);
        TileChunk rightChunkChunk = dimension.getChunkAt(decode(rightChunk).x, decode(rightChunk).y);
        TileChunk topRightChunkChunk = dimension.getChunkAt(decode(topRightChunk).x, decode(topRightChunk).y);
        TileChunk bottomRightChunkChunk = dimension.getChunkAt(decode(bottomRightChunk).x, decode(bottomRightChunk).y);
        TileChunk topLeftChunkChunk = dimension.getChunkAt(decode(topLeftChunk).x, decode(topLeftChunk).y);
        TileChunk bottomLeftChunkChunk = dimension.getChunkAt(decode(bottomLeftChunk).x, decode(bottomLeftChunk).y);

        HashMap<Biome, Integer> amounts = new HashMap<>();
        ArrayList<Biome> cannotBe = new ArrayList<>();

        if (
                (topChunkChunk != null || dimensionSpecified.containsKey(topChunk)) &&
                        (bottomChunkChunk != null || dimensionSpecified.containsKey(bottomChunk)) &&
                        (leftChunkChunk != null || dimensionSpecified.containsKey(leftChunk)) &&
                        (topChunkChunk != null || dimensionSpecified.containsKey(rightChunk))
        ) {

            for (Biome b : Biome.GENERIC.getDimensionBiomes(dimension.correspondingType)) {
                if (topChunkChunk != null) {
                    if (topChunkChunk.getType() == b) {
                        amounts.put(b, (amounts.getOrDefault(b, 0)) + 1);
                    }
                }
                if (bottomChunkChunk != null) {
                    if (bottomChunkChunk.getType() == b) {
                        amounts.put(b, (amounts.getOrDefault(b, 0)) + 1);
                    }
                }
                if (leftChunkChunk != null) {
                    if (leftChunkChunk.getType() == b) {
                        amounts.put(b, (amounts.getOrDefault(b, 0)) + 1);
                    }
                }
                if (rightChunkChunk != null) {
                    if (rightChunkChunk.getType() == b) {
                        amounts.put(b, (amounts.getOrDefault(b, 0)) + 1);
                    }
                }
                if (topRightChunkChunk != null) {
                    if (topRightChunkChunk.getType() == b) {
                        amounts.put(b, (amounts.getOrDefault(b, 0)) + 1);
                    }
                }
                if (bottomRightChunkChunk != null) {
                    if (bottomRightChunkChunk.getType() == b) {
                        amounts.put(b, (amounts.getOrDefault(b, 0)) + 1);
                    }
                }
                if (topLeftChunkChunk != null) {
                    if (topLeftChunkChunk.getType() == b) {
                        amounts.put(b, (amounts.getOrDefault(b, 0)) + 1);
                    }
                }
                if (bottomLeftChunkChunk != null) {
                    if (bottomLeftChunkChunk.getType() == b) {
                        amounts.put(b, (amounts.getOrDefault(b, 0)) + 1);
                    }
                }
            }

            for (Biome b : Biome.GENERIC.getDimensionBiomes(dimension.correspondingType)) {
                if (!amounts.containsKey(b)) {
                    cannotBe.add(b);
                } else {
                    if (amounts.get(b) < 2) {
                        cannotBe.add(b);
                    }
                }
            }

        }

        //System.out.println(amounts);
        System.out.println(cannotBe);

        if (cannotBe.size() >= Biome.GENERIC.getDimensionBiomes(dimension.correspondingType).size()) {
            cannotBe.clear();
        }

        Biome newBiome = getUndupedBiome(dimension, cannotBe.toArray(new Biome[0]));

        ArrayDeque<Long> biomeChunks = new ArrayDeque<>();
        biomeChunks.add(p);

        //System.out.println(newBiome);

        int chunkSize = 0;

        while (!biomeChunks.isEmpty()) {
            Long encoded = biomeChunks.poll();

            Pair<Integer> pos = new Pair<>((int) decode(encoded).x, (int) decode(encoded).y);

            long top = encode(pos.getA(), pos.getB() - chunkHeight);
            long bottom = encode(pos.getA(), pos.getB() + chunkHeight);
            long left = encode(pos.getA() - chunkWidth, pos.getB());
            long right = encode(pos.getA() + chunkWidth, pos.getB());

            if (rand.nextFloat() < newBiome.getSpreadChance() || newBiome.getMinChunkSize() > chunkSize) {
                if (!dimensionSpecified.containsKey(top) && dimension.getChunkAt(decode(top).x, decode(top).y) == null) {
                    biomeChunks.add(top);
                    chunkSize++;
                    dimensionSpecified.put(top, newBiome);
                }
            }
            if (rand.nextFloat() < newBiome.getSpreadChance() || newBiome.getMinChunkSize() > chunkSize) {
                if (!dimensionSpecified.containsKey(bottom) && dimension.getChunkAt(decode(bottom).x, decode(bottom).y) == null) {
                    biomeChunks.add(bottom);
                    chunkSize++;
                    dimensionSpecified.put(bottom, newBiome);
                }
            }
            if (rand.nextFloat() < newBiome.getSpreadChance() || newBiome.getMinChunkSize() > chunkSize) {
                if (!dimensionSpecified.containsKey(left) && dimension.getChunkAt(decode(left).x, decode(left).y) == null) {
                    biomeChunks.add(left);
                    chunkSize++;
                    dimensionSpecified.put(left, newBiome);
                }
            }
            if (rand.nextFloat() < newBiome.getSpreadChance() || newBiome.getMinChunkSize() > chunkSize) {
                if (!dimensionSpecified.containsKey(right) && dimension.getChunkAt(decode(right).x, decode(right).y) == null) {
                    biomeChunks.add(right);
                    chunkSize++;
                    dimensionSpecified.put(right, newBiome);
                }
            }
        }

        return newBiome;

    }

    public static HashMap<Long, TileType> getBlend(Biome type, Vector2f gridPos, Dimension dimension) {

        //Random and returned
        Random r = new Random();
        HashMap<Long, TileType> returned = new HashMap<>();
        ArrayList<Long> ignore = new ArrayList<>();

        TileChunk right = dimension.getChunkAt(gridPos.x + 8, gridPos.y);
        TileChunk left = dimension.getChunkAt(gridPos.x - 8, gridPos.y);
        TileChunk up = dimension.getChunkAt(gridPos.x, gridPos.y + 8);
        TileChunk down = dimension.getChunkAt(gridPos.x, gridPos.y - 8);

        //loop through all the chunk tiles
        for (int i = 0; i < chunkHeight; i++) {
            for (int j = 0; j < chunkWidth; j++) {
                if (ignore.contains(encode(j, i))) {
                    continue;
                }
                //Distance will be 0 if the tile is on the chunk border.
                int distance;
                if (j > (chunkWidth / 2f) - 0.5) {
                    //The tile that the loop is on is on the right of the chunk
                    //The chunk that is on the right of the chunk
                    //Check that the chunk exists
                    if (right != null) {
                        //The biome of the chunk next to this chunk
                        Biome biomeThere = right.getType();
                        //Check if the chunk next to this chunk is of a different biome differ type, meaning that the regular spread is not used.
                        if (biomeThere.getDifferType() != type.getDifferType()) {
                            //Check that the biome of the chunk next to this chunk has a higher chunk differ type bias, meaning that the biome
                            //would be on top of this biome.
                            if (biomeThere.getDifferType().getBias() > type.getDifferType().getBias()) {
                                //The distance of this strip
                                int dis = r.nextInt(chunkWidth / 2);
                                for (int l = 0; l < dis; l++) {
                                    long key = encode(chunkWidth - 1 - l, i);
                                    if (!returned.containsKey(key)) {
                                        returned.put(key, biomeThere.getRandomTileTypeFromBiome());
                                    }
                                }
                            }
                        } else {
                            if (biomeThere != type) {
                                distance = chunkWidth - j - 1;
                                int chance = 50 - chunkSpreadDiffer * distance;
                                if (r.nextFloat() < chance / 100f) {
                                    if (!returned.containsKey(encode(j, i))) {
                                        returned.put(encode(j, i), biomeThere.getRandomTileTypeFromBiome());
                                    }
                                }
                            }
                        }
                    }
                }
                if (j < (chunkWidth / 2f) - 0.5) {
                    //The tile that the loop is on is on the left of the chunk
                    if (left != null) {
                        Biome biomeThere = left.getType();
                        if (biomeThere.getDifferType() != type.getDifferType()) {
                            //Check that the biome of the chunk next to this chunk has a higher chunk differ type bias, meaning that the biome
                            //would be on top of this biome.
                            if (biomeThere.getDifferType().getBias() > type.getDifferType().getBias()) {
                                //The distance of this strip
                                int dis = r.nextInt(chunkWidth / 2);
                                for (int l = 0; l < dis; l++) {
                                    long key = encode(l, i);
                                    if (!returned.containsKey(key)) {
                                        returned.put(key, biomeThere.getRandomTileTypeFromBiome());
                                    }
                                }
                            }
                        } else {
                            if (biomeThere != type) {
                                distance = j;
                                int chance = 50 - chunkSpreadDiffer * distance;
                                if (r.nextFloat() < chance / 100f) {
                                    if (!returned.containsKey(encode(j, i))) {
                                        returned.put(encode(j, i), biomeThere.getRandomTileTypeFromBiome());
                                    }
                                }
                            }
                        }
                    }
                }
                if (i > (chunkHeight / 2f) - 0.5) {
                    //The tile that the loop is on is on the right of the chunk
                    if (down != null) {
                        Biome biomeThere = down.getType();
                        if (biomeThere.getDifferType() != type.getDifferType()) {
                            //Check that the biome of the chunk next to this chunk has a higher chunk differ type bias, meaning that the biome
                            //would be on top of this biome.
                            if (biomeThere.getDifferType().getBias() > type.getDifferType().getBias()) {
                                //The distance of this strip
                                int dis = r.nextInt(chunkHeight / 2);
                                for (int l = 0; l < dis; l++) {
                                    long key = encode(j, chunkHeight - 1 - l);
                                    if (!returned.containsKey(key)) {
                                        returned.put(key, biomeThere.getRandomTileTypeFromBiome());
                                    }
                                }
                            }
                        } else {
                            if (biomeThere != type) {
                                distance = chunkHeight - i - 1;
                                int chance = 50 - chunkSpreadDiffer * distance;
                                if (r.nextFloat() < chance / 100f) {
                                    if (!returned.containsKey(encode(j, i))) {
                                        returned.put(encode(j, i), biomeThere.getRandomTileTypeFromBiome());
                                    }
                                }
                            }
                        }
                    }
                }
                if (i < (chunkHeight / 2f) - 0.5) {
                    //The tile that the loop is on is on the left of the chunk
                    if (up != null) {
                        Biome biomeThere = up.getType();
                        if (biomeThere.getDifferType() != type.getDifferType()) {
                            //Check that the biome of the chunk next to this chunk has a higher chunk differ type bias, meaning that the biome
                            //would be on top of this biome.
                            if (biomeThere.getDifferType().getBias() > type.getDifferType().getBias()) {
                                //The distance of this strip
                                int dis = r.nextInt(chunkHeight / 2);
                                for (int l = 0; l < dis; l++) {
                                    long key = encode(j, l);
                                    if (!returned.containsKey(key)) {
                                        returned.put(key, biomeThere.getRandomTileTypeFromBiome());
                                    }
                                }
                            }
                        } else {
                            if (biomeThere != type) {
                                distance = i;
                                int chance = 50 - chunkSpreadDiffer * distance;
                                if (r.nextFloat() < chance / 100f) {
                                    if (!returned.containsKey(encode(j, i))) {
                                        returned.put(encode(j, i), biomeThere.getRandomTileTypeFromBiome());
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
        return returned;
    }

    private static Biome getUndupedBiome(Dimension dimension, Biome... ignore) {
        Biome b = getBiasedBiome(dimension, ignore);
        if (Arrays.asList(ignore).contains(b)) {
            return getUndupedBiome(dimension, ignore);
        }
        return b;
    }

    public static Biome getBiasedBiome(Dimension dimension, Biome... ignore) {
        int totalRandom = Biome.GENERIC.getTotalCreationChance(dimension.correspondingType, ignore);
        Random rand = new Random();
        int index = rand.nextInt(totalRandom);
        int sum = 0;
        int i = 0;

        List<Biome> items = Biome.GENERIC.getDimensionBiomes(dimension.correspondingType);
        for (Biome b : ignore) {
            items.remove(b);
        }
        while (sum < index) {
            sum = sum + items.get(i).getCreationChance();
            i++;
        }
        System.out.println(items.get(Math.max(0, i - 1)));
        return items.get(Math.max(0, i - 1));
    }


}
