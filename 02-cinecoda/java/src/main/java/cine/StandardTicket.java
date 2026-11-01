package cine;

public class StandardTicket implements Ticket {
    @Override
    public String getLabel() {
        return "Billet plein tarif";
    }

    @Override
    public double calculatePrice(double basePrice) {
        return basePrice;
    }
}
