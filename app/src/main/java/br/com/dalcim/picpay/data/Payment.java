package br.com.dalcim.picpay.data;

public class Payment {

    private long destinationUserId;
    private CreditCard creditCard;
    private double value;

    public Payment() {}

    public Payment(long destinationUserId, CreditCard creditCard, double value) {
        this.destinationUserId = destinationUserId;
        this.creditCard = creditCard;
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public long getDestinationUserId() {
        return destinationUserId;
    }

    public void setDestinationUserId(long destinationUserId) {
        this.destinationUserId = destinationUserId;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }
}
