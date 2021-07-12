package renderEngine.spriteHandler;

import engine.MainGameLoop;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import static global.GlobalMethods.encode;

public class SpriteSheet {

    //Integers
    public static int TILE_SIZE = 16;

    //Images
    private static final HashMap<String, HashMap<Long, BufferedImage>> sprites = new HashMap<>();

    public static BufferedImage getSprite(int xGrid, int yGrid, int gap, int tileWidth, int tileHeight, String path) {
        BufferedImage returned;
        if (sprites.containsKey(path)) {
            HashMap<Long, BufferedImage> pathSprites = sprites.get(path);
            long key = encode(xGrid, yGrid);
            if (pathSprites.containsKey(key)) {
                return pathSprites.get(key);
            } else {
                try {
                    returned = ImageIO.read(MainGameLoop.class.getResourceAsStream(path)).getSubimage(xGrid * (tileWidth + gap), yGrid * (tileHeight + gap), tileWidth, tileHeight);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
                pathSprites.put(encode(xGrid, yGrid), returned);
                return returned;
            }
        } else {
            HashMap<Long, BufferedImage> pathSprites = new HashMap<>();
            try {
                returned = ImageIO.read(MainGameLoop.class.getResourceAsStream(path)).getSubimage(xGrid * (tileWidth + gap), yGrid * (tileHeight + gap), tileWidth, tileHeight);
                pathSprites.put(encode(xGrid, yGrid), returned);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            sprites.put(path, pathSprites);
            return returned;
        }
    }
}