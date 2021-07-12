package enums;

public enum BiomeDifferType {
    GENERIC(0),
    FERTILE(1),
    DRY(2);

    private int bias;

    BiomeDifferType(int bias) {
        this.bias = bias;
    }

    public int getBias() {
        return bias;
    }

    public void setBias(int bias) {
        this.bias = bias;
    }
}
