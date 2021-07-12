package listeners;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;

public class KeyboardListener {

    public KeyboardListener() {
        try {
            Keyboard.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    public void checkClick() {
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            System.exit(0);
        }
    }

}
