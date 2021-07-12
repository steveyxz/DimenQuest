package entities;

import entities.dimensions.Dimension;
import entities.entity.moving.Player;
import entities.tiles.chunking.ChunkGenerator;
import global.Constants;
import global.GlobalValues;
import global.ParticleSystems;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import particles.ParticleSystem;

import java.util.Random;

import static global.Constants.tileWidth;

public class Camera {

    private final Vector3f position = new Vector3f(tileWidth / 2, -tileWidth / 2, 0f);
    private float pitch; //Up down
    private float yaw; //Left right
    private float roll; //Rotation (in degrees)

    private float speed = 0.01f;
    private int timer = 0;
    private Integer keyChangeTimer = null;
    private String lastKeyComb = "";

    public void frame(Player player) {
        player.setDimension(GlobalValues.currentDimension, this);
        String keyComb = "";
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            keyComb += "d";
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            keyComb += "a";
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            keyComb += "w";
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            keyComb += "s";
        }
        if (keyComb.contains("a") && keyComb.contains("d")) {
            keyComb = keyComb.replace('a', 'x');
            keyComb = keyComb.replace('d', 'x');
        }
        if (keyComb.contains("w") && keyComb.contains("s")) {
            keyComb = keyComb.replace('s', 'x');
            keyComb = keyComb.replace('w', 'x');
        }
        StringBuilder finalString = new StringBuilder();
        for (char c : keyComb.toCharArray()) {
            if (c == 'x') {
                continue;
            }
            finalString.append(c);
        }
        String string = new String(finalString);
        if (finalString.isEmpty()) {
            return;
        }
        if (!lastKeyComb.equals(string)) {
            if (keyChangeTimer == null) {
                keyChangeTimer = 6;
            }
            if (keyChangeTimer > Constants.playerWalkRenderChangeDelay) {
                keyChangeTimer = null;
            } else {
                keyChangeTimer++;
                return;
            }
        }
        switch (string) {
            case "a" -> {
                if (player.getWalking() != Constants.playerLeft) {
                    player.setLoop(Constants.playerLeft);
                }
            }
            case "d" -> {
                if (player.getWalking() != Constants.playerRight) {
                    player.setLoop(Constants.playerRight);
                }
            }
            case "w" -> {
                if (player.getWalking() != Constants.playerUp) {
                    player.setLoop(Constants.playerUp);
                }
            }
            case "s" -> {
                if (player.getWalking() != Constants.playerDown) {
                    player.setLoop(Constants.playerDown);
                }
            }
            case "aw" -> {
                if (player.getWalking() != Constants.playerUpLeft) {
                    player.setLoop(Constants.playerUpLeft);
                }
            }
            case "dw" -> {
                if (player.getWalking() != Constants.playerUpRight) {
                    player.setLoop(Constants.playerUpRight);
                }
            }
            case "as" -> {
                if (player.getWalking() != Constants.playerDownLeft) {
                    player.setLoop(Constants.playerDownLeft);
                }
            }
            case "ds" -> {
                if (player.getWalking() != Constants.playerDownRight) {
                    player.setLoop(Constants.playerDownRight);
                }
            }
        }
        lastKeyComb = string;
    }

    public void move(Dimension chunkHandler, Player player, ChunkGenerator generator) {
        if (player.getDisplayOn().contains(Constants.gameState)) {
            ParticleSystem walkingSystem = ParticleSystems.walkingParticles;
            float y;
            float x;
            Random r = new Random();
            float xOffset = r.nextFloat();
            float yOffset = r.nextFloat();
            //if (r.nextBoolean()) {
            xOffset = -xOffset;
            //}
            //if (r.nextBoolean()) {
            //yOffset = -yOffset;
            //}
            xOffset /= 50;
            yOffset /= 50;
            if (walkingSystem.getDirection() == null) {
                y = 0;
                x = 0;
            } else {
                y = walkingSystem.getDirection().y;
                x = walkingSystem.getDirection().x;
            }
            GlobalValues.isMoving = false;
            if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
                GlobalValues.isMoving = true;
                position.x += speed;
                player.getDimension().setPlayerPos((new Vector2f(player.getDimension().getPlayerPos().x + speed / Constants.tileWidth, player.getDimension().getPlayerPos().y)));

                walkingSystem.setDirection(new Vector3f(-speed, y, 0), 1f);
                ParticleSystems.walkingParticles.generateParticles(new Vector3f(player.getPosition().x + xOffset + player.getDimension().getPlayerPos().x * Constants.tileWidth, player.getPosition().y + yOffset + -player.getDimension().getPlayerPos().y * Constants.tileWidth, -0.2f));
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
                GlobalValues.isMoving = true;
                position.x -= speed;
                player.getDimension().setPlayerPos((new Vector2f(player.getDimension().getPlayerPos().x - speed / Constants.tileWidth, player.getDimension().getPlayerPos().y)));

                walkingSystem.setDirection(new Vector3f(speed, y, 0), 1f);
                ParticleSystems.walkingParticles.generateParticles(new Vector3f(player.getPosition().x + xOffset + player.getDimension().getPlayerPos().x * Constants.tileWidth, player.getPosition().y + yOffset + -player.getDimension().getPlayerPos().y * Constants.tileWidth, -0.2f));
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
                GlobalValues.isMoving = true;
                position.y += speed;
                player.getDimension().setPlayerPos((new Vector2f(player.getDimension().getPlayerPos().x, player.getDimension().getPlayerPos().y - speed / Constants.tileWidth)));

                walkingSystem.setDirection(new Vector3f(x, -speed, 0), 1f);
                ParticleSystems.walkingParticles.generateParticles(new Vector3f(player.getPosition().x + xOffset + player.getDimension().getPlayerPos().x * Constants.tileWidth, player.getPosition().y + yOffset + -player.getDimension().getPlayerPos().y * Constants.tileWidth, -0.2f));

            }
            if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
                GlobalValues.isMoving = true;
                position.y -= speed;
                player.getDimension().setPlayerPos((new Vector2f(player.getDimension().getPlayerPos().x, player.getDimension().getPlayerPos().y + speed / Constants.tileWidth)));

                walkingSystem.setDirection(new Vector3f(x, speed, 0), 1f);
                ParticleSystems.walkingParticles.generateParticles(new Vector3f(player.getPosition().x + xOffset + player.getDimension().getPlayerPos().x * Constants.tileWidth, player.getDimension().getPlayerPos().y + yOffset + -player.getDimension().getPlayerPos().y * Constants.tileWidth, -0.2f));

            }
            if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
                if (position.z > speed) {
                    position.z -= speed;
                }
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
                position.z += speed;
            }

            //System.out.println(player.getGridPos());
            if (!Constants.useNewThreadForChunks) {
                chunkHandler.loadDimension((int) Math.floor(player.getDimension().getPlayerPos().x), (int) Math.floor(player.getDimension().getPlayerPos().y), 4, 4, generator);
            }
            if (timer > Constants.playerWalkDelay && GlobalValues.isMoving) {
                player.nextLoop();
                timer = 0;
            }
            timer++;

            if (!GlobalValues.isMoving) {
                if (player.getWalking().equals(Constants.playerLeft)) {
                    player.getModel().getTexture().setTextureID(Constants.playerLeftPassive);
                } else if (player.getWalking().equals(Constants.playerRight)) {
                    player.getModel().getTexture().setTextureID(Constants.playerRightPassive);
                } else if (player.getWalking().equals(Constants.playerDown)) {
                    player.getModel().getTexture().setTextureID(Constants.playerDownPassive);
                } else if (player.getWalking().equals(Constants.playerUp)) {
                    player.getModel().getTexture().setTextureID(Constants.playerUpPassive);
                } else if (player.getWalking().equals(Constants.playerDownRight)) {
                    player.getModel().getTexture().setTextureID(Constants.playerDownRightPassive);
                } else if (player.getWalking().equals(Constants.playerDownLeft)) {
                    player.getModel().getTexture().setTextureID(Constants.playerDownLeftPassive);
                } else if (player.getWalking().equals(Constants.playerUpLeft)) {
                    player.getModel().getTexture().setTextureID(Constants.playerUpLeftPassive);
                } else if (player.getWalking().equals(Constants.playerUpRight)) {
                    player.getModel().getTexture().setTextureID(Constants.playerUpRightPassive);
                }


            }
        }
    }


    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Vector3f getPosition() {
        return position;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public Integer getKeyChangeTimer() {
        return keyChangeTimer;
    }

    public void setKeyChangeTimer(Integer keyChangeTimer) {
        this.keyChangeTimer = keyChangeTimer;
    }

    public String getLastKeyComb() {
        return lastKeyComb;
    }

    public void setLastKeyComb(String lastKeyComb) {
        this.lastKeyComb = lastKeyComb;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getRoll() {
        return roll;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }
}
