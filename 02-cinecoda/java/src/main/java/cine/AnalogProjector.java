package cine;

public class AnalogProjector implements Projector {
    private final String serialNumber;

    public AnalogProjector(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public AnalogProjector() {
        this("ARG-001");
    }

    @Override
    public String getSerialNumber() {
        return serialNumber;
    }

    @Override
    public String getTypeDescription() {
        return "projecteur argentique";
    }

    @Override
    public String toString() {
        return getTypeDescription();
    }
}
