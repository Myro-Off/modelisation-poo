package cine;

public class StudentTicket implements Ticket {
    @Override
    public String getLabel() {
        return "Billet étudiant";
    }

    @Override
    public double calculatePrice(double basePrice) {
        return basePrice * 0.75;
    }
}
