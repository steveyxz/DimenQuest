package classes;

public class Pair<T> extends Object {

    private T a;
    private T b;

    public Pair(T a, T b) {
        this.a = a;
        this.b = b;
    }

    public T getA() {
        return a;
    }

    public void setA(T a) {
        this.a = a;
    }

    public T getB() {
        return b;
    }

    public void setB(T b) {
        this.b = b;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pair) {
            Pair<?> pair = (Pair<?>) obj;
            return pair.getA().equals(getA()) && pair.getB().equals(getB());
        }
        return false;
    }

    public String valueOf() {
        return "Pair[a=" + getA() + ", b=" + getB() + "]";
    }
}
