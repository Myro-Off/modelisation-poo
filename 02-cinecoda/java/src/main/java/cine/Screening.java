package cine;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Screening {
    private final FilmCopy filmCopy;
    private final Room room;
    private final LocalDate date;
    private final double basePrice;
    private final List<Ticket> tickets = new ArrayList<>();
    private boolean started = false;
    private boolean finished = false;

    public Screening(FilmCopy filmCopy, Room room, LocalDate date, double basePrice) {
        this.filmCopy = filmCopy;
        this.room = room;
        this.date = date;
        this.basePrice = basePrice;
    }

    public FilmCopy getFilmCopy() {
        return filmCopy;
    }

    public Movie getMovie() {
        return filmCopy.getMovie();
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public List<Ticket> getTickets() {
        return Collections.unmodifiableList(tickets);
    }

    public double sellTicket(Ticket ticket) throws BusinessRuleException {
        if (tickets.size() >= room.getCapacity()) {
            throw new BusinessRuleException(
                    String.format("la séance est complète (%d places)", room.getCapacity())
            );
        }
        tickets.add(ticket);
        return ticket.calculatePrice(basePrice);
    }

    public int getRemainingSeats() {
        return room.getCapacity() - tickets.size();
    }

    public int getOccupancyRate() {
        if (room.getCapacity() == 0) {
            return 0;
        }
        return (tickets.size() * 100) / room.getCapacity();
    }

    public double calculateRevenue() {
        return tickets.stream()
                .mapToDouble(ticket -> ticket.calculatePrice(basePrice))
                .sum();
    }

    public void start() throws BusinessRuleException {
        if (!room.isAvailable()) {
            throw new BusinessRuleException(
                    String.format("la Salle %d n'est pas libre (état : %s)", room.getNumber(), room.getState())
            );
        }
        filmCopy.checkPlayable(room.getProjector(), date);
        room.startScreening();
        this.started = true;
    }

    public void finish() throws BusinessRuleException {
        if (!started || finished) {
            throw new BusinessRuleException("la séance ne peut pas être terminée");
        }
        room.finishScreening();
        filmCopy.recordProjection();
        this.finished = true;
    }
}
