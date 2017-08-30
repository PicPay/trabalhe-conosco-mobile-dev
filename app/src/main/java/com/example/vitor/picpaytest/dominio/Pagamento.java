package com.example.vitor.picpaytest.dominio;

/**
 * Created by Vitor on 22/08/2017.
 */

public class Pagamento {
    private String card_number;
    private int cvv;
    private double value;
    private String expiry_date;
    private int destination_user_id;

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
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

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public int getDestination_user_id() {
        return destination_user_id;
    }

    public void setDestination_user_id(int destination_user_id) {
        this.destination_user_id = destination_user_id;
    }
}
