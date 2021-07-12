package text.fontRendering;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.shaders.ShaderProgram;

public class FontShader extends ShaderProgram {

    private static final String VERTEX_FILE = "/fontVertex.shader";
    private static final String FRAGMENT_FILE = "/fontFragment.shader";

    private int location_color;
    private int location_translation;

    public FontShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        location_color = super.getUniformVariable("colour");
        location_translation = super.getUniformVariable("translation");
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(0, "position");
        bindAttribute(1, "textureCoords");
    }

    protected void loadColor(Vector3f color) {
        super.loadVector3f(location_color, color);
    }

    protected void loadTranslation(Vector2f translation) {
        super.loadVector2f(location_translation, translation);
    }


}
