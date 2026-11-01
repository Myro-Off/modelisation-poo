package cine;

public enum RoomState {
    FREE("LIBRE"),
    IN_SCREENING("EN_SEANCE"),
    CLEANING("NETTOYAGE");

    private final String displayName;

    RoomState(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
