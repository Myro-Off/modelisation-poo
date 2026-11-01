package cine;

public class Room {
    private final int number;
    private final int capacity;
    private final Projector projector;
    private RoomState state;

    public Room(int number, int capacity, Projector projector) {
        this.number = number;
        this.capacity = capacity;
        this.projector = projector;
        this.state = RoomState.FREE;
    }

    public int getNumber() {
        return number;
    }

    public int getCapacity() {
        return capacity;
    }

    public Projector getProjector() {
        return projector;
    }

    public RoomState getState() {
        return state;
    }

    public boolean isAvailable() {
        return state == RoomState.FREE;
    }

    public void startScreening() {
        if (state != RoomState.FREE) {
            throw new IllegalStateException(
                    String.format("la Salle %d n'est pas libre (état : %s)", number, state)
            );
        }
        this.state = RoomState.IN_SCREENING;
    }

    public void finishScreening() {
        this.state = RoomState.CLEANING;
    }

    public void finishCleaning() {
        if (state != RoomState.CLEANING) {
            throw new IllegalStateException(
                    String.format("la Salle %d n'est pas en nettoyage", number)
            );
        }
        this.state = RoomState.FREE;
    }

    @Override
    public String toString() {
        return String.format("Salle %d : %d places, %s", number, capacity, projector.getTypeDescription());
    }
}
