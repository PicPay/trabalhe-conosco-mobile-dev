package com.lacourt.picpay.picpaymobiledev;

/**
 * Created by igor on 18/08/2017.
 */

public class TransferData {
    String cardNumber;
    String cvv;
    String value;
    String expiryDate;
    String destinationUserId;

    public TransferData(){}

    public TransferData(String cardNumber, String cvv, String value, String expiryDate, String destinationUserId) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.value = value;
        this.expiryDate = expiryDate;
        this.destinationUserId = destinationUserId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getDestinationUserId() {
        return destinationUserId;
    }

    public void setDestinationUserId(String destinationUserId) {
        this.destinationUserId = destinationUserId;
    }
}
