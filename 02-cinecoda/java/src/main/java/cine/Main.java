package cine;

import java.time.LocalDate;
import java.util.Locale;
import java.util.logging.Logger;

public class Main {
    private static Logger log;

    public static void main(String[] args) throws BusinessRuleException {
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

        // === 3. Dune 3 en Salle 1 le 15/10/2026 (tarif de base 10,00 €) ===
        var day = LocalDate.of(2026, 10, 15);
        log.info("\n=== 3. Dune 3 en Salle 1 le 15/10/2026 (tarif de base 10,00 €) ===");
        var screening1 = cinema.scheduleScreening(1, duneDigital, day, 10.0);
        sellTicket(screening1, new StandardTicket());
        sellTicket(screening1, new StandardTicket());
        sellTicket(screening1, new StudentTicket());
        sellTicket(screening1, new ChildTicket());
        log.info(() -> "Places restantes : " + screening1.getRemainingSeats());
        log.info(() -> "Taux de remplissage : " + screening1.getOccupancyRate() + " %");
        log.info(() -> String.format(Locale.FRANCE, "Recette : %.2f €", screening1.calculateRevenue()));
    }

    private static void sellTicket(Screening screening, Ticket ticket) {
        try {
            double price = screening.sellTicket(ticket);
            log.info(String.format(Locale.FRANCE, "%s vendu : %.2f €", ticket.getLabel(), price));
        } catch (BusinessRuleException e) {
            log.info("Vente refusée : " + e.getMessage());
        }
    }
}
