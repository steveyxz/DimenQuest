package renderEngine.spriteHandler;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.BufferedImageUtil;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteGlobal {

    public static int loadTexture(String fileName, int width, int height, int gap, int x, int y) {
        try {
            BufferedImage b = SpriteSheet.getSprite(x, y, gap, width, height, "/" + fileName + ".png");
            Texture t = BufferedImageUtil.getTexture(fileName, b);
            return t.getTextureID();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
