package entities.entity.moving;

import classes.ListLooper;
import entities.Camera;
import entities.dimensions.Dimension;
import entities.entity.Entity;
import enums.EntityType;
import enums.GameState;
import global.Constants;
import gui.GuiRenderer;
import inventory.PlayerInventory;
import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Loader;
import renderEngine.spriteHandler.SpriteGlobal;
import textures.ModelTexture;

import java.util.ArrayList;

import static global.Constants.tileWidth;

public class Player extends Entity {

    private final Camera camera;
    private ArrayList<Integer> walking;
    private Dimension dimension;
    private final int timer = 0;
    private final ListLooper<Integer> walkingLoop;
    private final PlayerInventory inventory;

    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, Camera camera, Loader loader, GameState[] display, ArrayList<Integer> playerWalking, Dimension dimension, GuiRenderer guiRenderer) {
        super(model, position, rotX, rotY, rotZ, 1, EntityType.PLAYER, display);
        this.walking = playerWalking;
        this.camera = camera;
        this.dimension = dimension;
        this.walkingLoop = new ListLooper<>(walking);
        this.inventory = new PlayerInventory(this, Constants.quickbarSize, guiRenderer);
    }

    public static TexturedModel generatePlayerModel(Loader loader) {
        float offset = 0;
        float[] vertices = {
                -offset, offset, 0f,//v0
                -offset, -tileWidth + offset, 0f,//v1
                tileWidth - offset, -tileWidth + offset, 0f,//v2
                tileWidth - offset, offset, 0f,//v3
        };

        int[] indices = {
                0, 1, 3,//top left triangle (v0, v1, v3)
                3, 1, 2//bottom right triangle (v3, v1, v2)
        };

        float[] textures = {
                0, 0,
                0, 1,
                1, 1,
                1, 0
        };

        return new TexturedModel(loader.loadToVAO(vertices, textures, indices), new ModelTexture(SpriteGlobal.loadTexture("entities", Constants.entitySpriteSheetTextureWidth, Constants.entitySpriteSheetTextureHeight, Constants.entitySpriteSheetGap, 0, 0)));
    }

    public void throwItem(int slot) {

    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension, Camera camera) {
        if (dimension != this.dimension) {
            this.dimension = dimension;
            camera.getPosition().set(this.getDimension().getPlayerPos().x / (camera.getSpeed() / tileWidth) * camera.getSpeed(), -this.getDimension().getPlayerPos().y / (camera.getSpeed() / tileWidth) * camera.getSpeed(), 0.01f);
        }
    }

    public Camera getCamera() {
        return camera;
    }

    @Override
    public void tick() {

    }

    public void nextLoop() {
        this.model.getTexture().setTextureID(this.walkingLoop.next());
    }

    public void setLoop(ArrayList<Integer> loop) {
        this.walking = loop;
        walkingLoop.setList(walking);
        walkingLoop.setOn(0);
    }

    public ArrayList<Integer> getWalking() {
        return walking;
    }
}
