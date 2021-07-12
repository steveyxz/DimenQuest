package entities.entity;

import enums.EntityType;
import enums.GameState;
import models.TexturedModel;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Entity {

    protected final ArrayList<GameState> displayOn = new ArrayList<>();
    protected TexturedModel model;
    protected Vector3f position;
    protected Vector2f gridPos;
    protected float rotX, rotY, rotZ;
    protected float scale;
    protected EntityType entityType;
    protected int zIndex = 0;

    public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, EntityType entityType, GameState[] display) {
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
        this.entityType = entityType;
        this.gridPos = new Vector2f(0, 0);
        displayOn.addAll(Arrays.asList(display));
    }

    public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, EntityType entityType, GameState[] display, Vector2f gridPos) {
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
        this.entityType = entityType;
        this.gridPos = gridPos;
        displayOn.addAll(Arrays.asList(display));
    }

    public int getZIndex() {
        return zIndex;
    }

    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    public ArrayList<GameState> getDisplayOn() {
        return displayOn;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public Vector2f getGridPos() {
        return gridPos;
    }

    public void setGridPos(Vector2f gridPos) {
        this.gridPos = gridPos;
    }

    public void increaseGridX(int x) {
        this.gridPos.x += x;
    }

    public void increaseGridY(int y) {
        this.gridPos.y += y;
    }

    public void increaseGridPos(int x, int y) {
        this.gridPos.x += x;
        this.gridPos.y += y;
    }

    public void increaseGridPos(Vector2f xy) {
        this.gridPos.x += xy.x;
        this.gridPos.y += xy.y;
    }

    public void increasePosition(float dx, float dy, float dz) {
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
    }

    public void increaseRotation(float dx, float dy, float dz) {
        this.rotX += dx;
        this.rotY += dy;
        this.rotZ += dz;
    }

    public TexturedModel getModel() {
        return model;
    }

    public void setModel(TexturedModel model) {
        this.model = model;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getRotX() {
        return rotX;
    }

    public void setRotX(float rotX) {
        this.rotX = rotX;
    }

    public float getRotY() {
        return rotY;
    }

    public void setRotY(float rotY) {
        this.rotY = rotY;
    }

    public float getRotZ() {
        return rotZ;
    }

    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public abstract void tick();
}
