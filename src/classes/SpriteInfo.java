package classes;

public class SpriteInfo {
    private int x;
    private int y;
    private int width;
    private int height;
    private int gap;
    private String spriteSheet;

    public SpriteInfo(int x, int y, int width, int height, int gap, String spriteSheet) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.gap = gap;
        this.spriteSheet = spriteSheet;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }

    public String getSpriteSheet() {
        return spriteSheet;
    }

    public void setSpriteSheet(String spriteSheet) {
        this.spriteSheet = spriteSheet;
    }
}
