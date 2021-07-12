package renderEngine.shaders.texture;

import entities.Camera;
import org.lwjgl.util.vector.Matrix4f;
import renderEngine.shaders.ShaderProgram;
import tools.maths.TransformationMaths;

public class TextureShader extends ShaderProgram {

    private static final String VERTEX_FILE = "/imageVertex.shader";
    private static final String FRAGMENT_FILE = "/imageFragment.shader";

    private static int transformationUniform = 0;
    private static int projectionMatrix = 0;
    private static int viewMatrix = 0;

    public TextureShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        transformationUniform = getUniformVariable("transformationMatrix");
        projectionMatrix = getUniformVariable("projectionMatrix");
        viewMatrix = getUniformVariable("viewMatrix");
    }

    public void changeTransformations(Matrix4f value) {
        super.loadMatrix(transformationUniform, value);
    }

    public void changeProjections(Matrix4f value) {
        super.loadMatrix(projectionMatrix, value);
    }

    public void changeView(Camera value) {
        Matrix4f view = TransformationMaths.createViewMatrix(value);
        super.loadMatrix(viewMatrix, view);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
    }
}
