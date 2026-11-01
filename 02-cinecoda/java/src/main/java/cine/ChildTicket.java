package cine;

public class ChildTicket implements Ticket {
    private static final double FIXED_PRICE = 5.0;

    @Override
    public String getLabel() {
        return "Billet enfant";
    }

    @Override
    public double calculatePrice(double basePrice) {
        return FIXED_PRICE;
    }
}
