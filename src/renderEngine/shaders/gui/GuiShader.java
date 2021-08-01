package renderEngine.shaders.gui;

import entities.Camera;
import org.lwjgl.util.vector.Matrix4f;
import renderEngine.shaders.ShaderProgram;
import tools.maths.TransformationMaths;

public class GuiShader extends ShaderProgram {

    private static final String VERTEX_FILE = "/guiVertexShader.shader";
    private static final String FRAGMENT_FILE = "/guiFragmentShader.shader";

    private int location_transformationMatrix;
    private int location_viewMatrix;

    public GuiShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    public void loadTransformation(Matrix4f matrix) {
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    public void changeView(Camera c) {
        Matrix4f matrix = TransformationMaths.createViewMatrix(c);
        super.loadMatrix(location_viewMatrix, matrix);
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformVariable("transformationMatrix");
        location_viewMatrix = super.getUniformVariable("viewMatrix");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }


}
