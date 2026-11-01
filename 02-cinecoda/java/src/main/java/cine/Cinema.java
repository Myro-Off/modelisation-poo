package cine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Cinema {
    private final String name;
    private final List<Room> rooms = new ArrayList<>();

    public Cinema(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Room createRoom(int number, int capacity, Projector projector) {
        Room room = new Room(number, capacity, projector);
        rooms.add(room);
        return room;
    }

    public List<Room> getRooms() {
        return Collections.unmodifiableList(rooms);
    }

    public Optional<Room> findRoom(int number) {
        return rooms.stream()
                .filter(room -> room.getNumber() == number)
                .findFirst();
    }

    public List<Room> getAvailableRooms() {
        return rooms.stream()
                .filter(Room::isAvailable)
                .toList();
    }

    public String formatAvailableRooms() {
        return getAvailableRooms().stream()
                .map(room -> "Salle " + room.getNumber())
                .collect(Collectors.joining(", "));
    }
}
