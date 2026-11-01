package cine;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Cinema {
    private final String name;
    private final List<Room> rooms = new ArrayList<>();
    private final List<Screening> screenings = new ArrayList<>();

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

    public Screening scheduleScreening(int roomNumber, FilmCopy filmCopy, LocalDate date, double basePrice)
            throws BusinessRuleException {
        Room room = findRoom(roomNumber)
                .orElseThrow(() -> new BusinessRuleException(
                        String.format("le cinéma %s n'a pas de salle %d", name, roomNumber)
                ));
        Screening screening = new Screening(filmCopy, room, date, basePrice);
        screenings.add(screening);
        return screening;
    }

    public List<Screening> getScreenings() {
        return Collections.unmodifiableList(screenings);
    }

    public List<Screening> getScreeningsOn(LocalDate date) {
        return screenings.stream()
                .filter(s -> s.getDate().equals(date))
                .toList();
    }

    public double calculateDailyRevenue(LocalDate date) {
        return getScreeningsOn(date).stream()
                .mapToDouble(Screening::calculateRevenue)
                .sum();
    }

    public String formatDailyRevenue(LocalDate date) {
        return String.format(java.util.Locale.FRANCE, "Recette du jour : %.2f €", calculateDailyRevenue(date));
    }
}
