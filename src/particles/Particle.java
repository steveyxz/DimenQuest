package particles;


import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;

public class Particle {

    private final Vector3f position;
    private final Vector3f speed;
    private final float gravityEffect;
    private final float lifeLength;
    private final float rotation;
    private final float scale;

    private final ParticleTexture texture;

    private float elapsedTime = 0;

    public Particle(ParticleTexture texture, Vector3f position, Vector3f speed, float gravityEffect, float lifeLength, float rotation, float scale) {
        this.position = position;
        this.speed = speed;
        this.gravityEffect = gravityEffect;
        this.lifeLength = lifeLength;
        this.rotation = rotation;
        this.scale = scale;
        this.texture = texture;
        ParticleMaster.addParticle(this);
    }

    public ParticleTexture getTexture() {
        return texture;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }

    protected boolean update() {
        Vector3f change = new Vector3f(speed);
        change.scale(DisplayManager.getFrameTimeSeconds());
        Vector3f.add(change, position, position);
        elapsedTime += DisplayManager.getFrameTimeSeconds();
        return elapsedTime < lifeLength;
    }
}
