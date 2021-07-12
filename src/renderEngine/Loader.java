package renderEngine;

import engine.MainGameLoop;
import models.RawModel;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Loader {

    //Lists of the textures, vaos and the vbos for easy clean up.
    private final List<Integer> vaos = new ArrayList<>();
    private final List<Integer> vbos = new ArrayList<>();
    private final List<Integer> textures = new ArrayList<>();

    /**
     * Loads a series of positions, texture offsets and sequence orders
     * into a RawModel.
     *
     * @param positions     The positions of the points
     * @param textureCoords The corresponding positions of the texture to render onto the points.
     * @param indices       The order on which to render the points in.
     * @return The raw model
     */
    public RawModel loadToVAO(float[] positions, float[] textureCoords, int[] indices) {
        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0, 3, positions);
        storeDataInAttributeList(1, 2, textureCoords);
        unbindVAO();
        return new RawModel(vaoID, indices.length);
    }

    public RawModel loadToVAO(float[] positions) {
        int vaoID = createVAO();
        storeDataInAttributeList(0, 2, positions);
        unbindVAO();
        return new RawModel(vaoID, positions.length / 2);
    }

    public int loadToVAO(float[] positions, float[] textureCoords) {
        int vaoID = createVAO();
        storeDataInAttributeList(0, 2, positions);
        storeDataInAttributeList(1, 2, textureCoords);
        unbindVAO();
        return vaoID;
    }

    /**
     * Loads a texture to be used inside a textured model
     *
     * @param fileName THe name of the file.
     * @return The ID of the texture
     */
    public int loadTexture(String fileName) {
        Texture texture = null;
        try {
            texture = TextureLoader.getTexture("PNG", Objects.requireNonNull(MainGameLoop.class.getResourceAsStream("/" + fileName + ".png")));
            GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
                    GL11.GL_LINEAR_MIPMAP_LINEAR);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -0.4f);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Tried to load texture " + fileName + ".png , didn't work");
            System.exit(-1);
        }
        textures.add(texture.getTextureID());
        return texture.getTextureID();
    }

    public Texture loadTextureForTexture(String fileName) {
        Texture texture = null;
        try {
            texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/" + fileName + ".png"));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Tried to load texture " + fileName + ".png , didn't work");
            System.exit(-1);
        }
        textures.add(texture.getTextureID());
        return texture;
    }

    /**
     * Cleans up (on window close)
     */
    public void cleanUp() {
        for (int vao : vaos) {
            GL30.glDeleteVertexArrays(vao);
        }
        for (int vba : vbos) {
            GL15.glDeleteBuffers(vba);
        }
        for (int texture : textures) {
            GL11.glDeleteTextures(texture);
        }
    }

    /**
     * Binds the indices of the model to the vbo ID
     *
     * @param indices The indices to bind.
     */
    private void bindIndicesBuffer(int[] indices) {
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }

    private IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    /**
     * Creates a new VAO
     *
     * @return The ID of the VAO
     */
    private int createVAO() {
        int vaoID = GL30.glGenVertexArrays();
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }

    /**
     * Stores a piece of data into a VBO
     *
     * @param attributeNumber The ID of the VBO.
     * @param coordSize       The dimensions of the data.
     * @param data            The data.
     */
    private void storeDataInAttributeList(int attributeNumber, int coordSize, float[] data) {
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        //Get the VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        //Store the data into the VBO
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        //Bind to the VAO
        GL20.glVertexAttribPointer(attributeNumber, coordSize, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private void unbindVAO() {
        GL30.glBindVertexArray(0);
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(data.length);
        floatBuffer.put(data);
        floatBuffer.flip();
        return floatBuffer;
    }

}
