package xyz.n7mn.dev.gunwar.util;

public class InvertibleObject<T> {

    private T value;
    private boolean inverted;

    public InvertibleObject(T value, boolean isInverted) {
        this.value = value;
        this.inverted = isInverted;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean isInverted() {
        return inverted;
    }

    public void setInverted(boolean inverted) {
        this.inverted = inverted;
    }

}
