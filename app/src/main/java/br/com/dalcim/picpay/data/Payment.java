package br.com.dalcim.picpay.data;

import com.google.gson.annotations.SerializedName;

/**
 * @author Wiliam
 * @since 25/08/2017
 */
public class Payment {

    private double value;
    @SerializedName("destination_user_id")
    private long destinationUserId;
    private CreditCard creditCard;

    public Payment() {
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
