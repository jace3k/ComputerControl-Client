package pl.jacekpiszczek.androidapp.elements;

public class DelayElement {
    private final String name;
    private final int value;

    public DelayElement(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
