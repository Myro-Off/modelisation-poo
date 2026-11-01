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

        // === 4. Avengers: Doomsday en Salle 1 le 15/10/2026 (tarif de base 10,00 €) ===
        log.info("\n=== 4. Avengers: Doomsday en Salle 1 le 15/10/2026 (tarif de base 10,00 €) ===");
        var screening2 = cinema.scheduleScreening(1, avengersDigital, day, 10.0);
        startScreening(screening2);

        // === 5. I Play Rocky en Salle 1 le 15/10/2026 (tarif de base 10,00 €) ===
        log.info("\n=== 5. I Play Rocky en Salle 1 le 15/10/2026 (tarif de base 10,00 €) ===");
        var screening3 = cinema.scheduleScreening(1, rocky35mm, day, 10.0);
        startScreening(screening3);
        log.info(() -> "État de la Salle 1 : " + room1.getState());

        // === 6. I Play Rocky en Salle 2 le 15/10/2026 (tarif de base 8,00 €) ===
        log.info("\n=== 6. I Play Rocky en Salle 2 le 15/10/2026 (tarif de base 8,00 €) ===");
        var screening4 = cinema.scheduleScreening(2, rocky35mm, day, 8.0);
        sellTicket(screening4, new StudentTicket());
        sellTicket(screening4, new StudentTicket());
        sellTicket(screening4, new StudentTicket());
        sellTicket(screening4, new StudentTicket());
        log.info(() -> "Places restantes : " + screening4.getRemainingSeats());
        log.info(() -> "Taux de remplissage : " + screening4.getOccupancyRate() + " %");
        startScreening(screening4);
        screening4.finish();
        log.info("Séance terminée");
        log.info(() -> "État de la Salle 2 : " + room2.getState());
        log.info(() -> "Salles libres : " + cinema.formatAvailableRooms());
        log.info(() -> "Projections de la copie de « I Play Rocky » : " + rocky35mm.getProjectionCount());

        // === 7. I Play Rocky en Salle 2 le 15/10/2026 (tarif de base 8,00 €) ===
        log.info("\n=== 7. I Play Rocky en Salle 2 le 15/10/2026 (tarif de base 8,00 €) ===");
        var screening5 = cinema.scheduleScreening(2, rocky35mm, day, 8.0);
        startScreening(screening5);
        room2.finishCleaning();
        log.info("Nettoyage de la Salle 2 terminé");
        startScreening(screening5);
    }

    private static void sellTicket(Screening screening, Ticket ticket) {
        try {
            double price = screening.sellTicket(ticket);
            log.info(String.format(Locale.FRANCE, "%s vendu : %.2f €", ticket.getLabel(), price));
        } catch (BusinessRuleException e) {
            log.info("Vente refusée : " + e.getMessage());
        }
    }

    private static void startScreening(Screening screening) {
        try {
            screening.start();
            log.info("Séance démarrée");
        } catch (BusinessRuleException e) {
            log.info("Démarrage refusé : " + e.getMessage());
        }
    }
}
