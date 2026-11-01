package cine;

public class DigitalProjector implements Projector {
    private final String serialNumber;

    public DigitalProjector(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public DigitalProjector() {
        this("NUM-001");
    }

    @Override
    public String getSerialNumber() {
        return serialNumber;
    }

    @Override
    public String getTypeDescription() {
        return "projecteur numérique";
    }

    @Override
    public String toString() {
        return getTypeDescription();
    }
}
