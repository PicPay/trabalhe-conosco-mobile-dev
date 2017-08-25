package com.picpay.picpayproject.model;

/**
 * Created by felip on 22/07/2017.
 */

public class Transferencia {
    private String cardNumber;
    private String cvv;
    private String value;
    private String expiryDate;
    private String destinationUser;
    private Boolean concluido;

    public Transferencia(String cardNumber, String cvv, String value, String expiryDate, String destinationUser) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.value = value;
        this.expiryDate = expiryDate;
        this.destinationUser = destinationUser;
    }

    public Boolean getConcluido() {
        return concluido;
    }

    public void setConcluido(Boolean concluido) {
        this.concluido = concluido;
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

    public String getDestinationUser() {
        return destinationUser;
    }

    public void setDestinationUser(String destinationUser) {
        this.destinationUser = destinationUser;
    }
}
