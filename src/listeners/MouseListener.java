package listeners;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;

public class MouseListener {

    private final double cooldown = 0;

    public MouseListener() {
        try {
            Mouse.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkBounds(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < height + y;
    }

    public static boolean isMouseClickWithin(int x, int y, int width, int height) {
        if (!Mouse.isButtonDown(0)) {
            return false;
        }
        return checkBounds(Mouse.getX(), Mouse.getY(), x, y, width, height);
    }

    public void checkClick() {
        if (Mouse.isButtonDown(0)) {

        }
    }
}
