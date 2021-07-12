package entities.tiles;

import entities.entity.Entity;
import enums.EntityType;
import enums.GameState;
import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

public class RectQuad extends Entity {
    public RectQuad(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, EntityType entityType, GameState[] display) {
        super(model, position, rotX, rotY, rotZ, scale, entityType, display);
    }

    @Override
    public void tick() {

    }
}
