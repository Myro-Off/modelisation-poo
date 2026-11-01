package cine;

import java.time.LocalDate;
import java.util.logging.Logger;

public class Main {
    private static Logger log;

    public static void main(String[] args) {
        log = ConsoleLogger.create("cine.scenario");

        // === 1. Création du cinéma CinéCoda ===
        log.info("=== 1. Création du cinéma CinéCoda ===");
        var cinema = new Cinema("CinéCoda");
        var room1 = cinema.createRoom(1, 100, new DigitalProjector());
        var room2 = cinema.createRoom(2, 3, new AnalogProjector());

        log.info(room1::toString);
        log.info(room2::toString);
        log.info(() -> "Salles libres : " + cinema.formatAvailableRooms());

        // === 2. Films et supports ===
        log.info("\n=== 2. Films et supports ===");
        var dune = new Movie("Dune 3", 166);
        var avengers = new Movie("Avengers: Doomsday", 119);
        var rocky = new Movie("I Play Rocky", 103);

        var duneDigital = new DigitalCopy(dune, LocalDate.of(2026, 10, 30));
        var avengersDigital = new DigitalCopy(avengers, LocalDate.of(2026, 10, 14));
        var rocky35mm = new Film35mmCopy(rocky, 49);

        log.info(duneDigital::toString);
        log.info(avengersDigital::toString);
        log.info(rocky35mm::toString);
    }
}
