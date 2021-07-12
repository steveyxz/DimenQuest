package entities.entity.moving;

import classes.SpriteInfo;
import entities.entity.Entity;
import enums.EntityType;
import enums.GameState;
import global.GlobalMethods;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Loader;

import static global.Constants.tileWidth;

public class Zombie extends Entity {

    public Zombie(SpriteInfo texture, Vector3f position, float rotX, float rotY, float scale, GameState[] display, Loader loader) {
        super(GlobalMethods.getSquareModelFromTexture(texture, loader, tileWidth, tileWidth), position, rotX, rotY, 1, scale, EntityType.ZOMBIE, display);
    }

    public Zombie(SpriteInfo texture, Vector3f position, float rotX, float rotY, float scale, GameState[] display, Vector2f gridPos, Loader loader) {
        super(GlobalMethods.getSquareModelFromTexture(texture, loader, tileWidth, tileWidth), position, rotX, rotY, 1, scale, EntityType.ZOMBIE, display, gridPos);
    }

    @Override
    public void tick() {

    }
}
