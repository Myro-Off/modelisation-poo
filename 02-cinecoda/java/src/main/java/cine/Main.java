package cine;

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
    }
}
