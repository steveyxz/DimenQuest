package particles;

import classes.SpriteInfo;
import renderEngine.spriteHandler.SpriteGlobal;

public class ParticleTexture {

    private final int textureID;
    private final SpriteInfo info;

    public ParticleTexture(int textureID, SpriteInfo info) {
        this.textureID = textureID;
        this.info = info;
    }

    public static ParticleTexture generateTexture(String spriteSheet, int x, int y, int width, int height, int gap) {
        return new ParticleTexture(SpriteGlobal.loadTexture(spriteSheet, width, height, gap, x, y), new SpriteInfo(x, y, width, height, gap, spriteSheet));
    }

    public int getTextureID() {
        return textureID;
    }

    public SpriteInfo getInfo() {
        return info;
    }
}
