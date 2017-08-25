package br.com.dalcim.picpay.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Wiliam on 25/08/2017.
 */

public class Payment {
    @SerializedName("card_number")
    private String cardNumber;
    private int cvv;
    private double value;
    @SerializedName("expiry_data")
    private String expiryData;
    @SerializedName("destination_user_id")
    private long destinationUserId;

    public Payment() {
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getExpiryData() {
        return expiryData;
    }

    public void setExpiryData(String expiryData) {
        this.expiryData = expiryData;
    }

    public long getDestinationUserId() {
        return destinationUserId;
    }

    public void setDestinationUserId(long destinationUserId) {
        this.destinationUserId = destinationUserId;
    }
}
