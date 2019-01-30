package pl.jacekpiszczek.androidapp.elements;

public class HostElement {
    private final String address;
    private final String name;

    public HostElement(String address, String name) {
        this.address = address;
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
