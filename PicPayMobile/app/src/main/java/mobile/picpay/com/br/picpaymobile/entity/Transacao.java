package mobile.picpay.com.br.picpaymobile.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

/**
 * Created by johonatan on 07/10/2017.
 */

public class Transacao {

    private String card_number;
    private int cvv;
    private Double valor;
    private String expiry_date;
    private int destination_user_id;
    private String status;
    private boolean success;
    //private boolean transaction;

    public Transacao(String card_number, int cvv, Double valor, String expiry_date, int destination_user_id) {
        this.card_number = card_number;
        this.cvv = cvv;
        this.valor = valor;
        this.expiry_date = expiry_date;
        this.destination_user_id = destination_user_id;

    }

    public Transacao() {

    }

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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDestination_user_id(int destination_user_id) {
        this.destination_user_id = destination_user_id;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean sucesso) {
        this.success = success;
    }




    @Override
    public String toString() {
        return "Transacao{" +
                "card_number='" + card_number + '\'' +
                ", cvv='" + cvv + '\'' +
                ", valor=" + valor +
                ", expiry_date='" + expiry_date + '\'' +
                ", destination_user_id='" + destination_user_id + '\'' +
                '}';
    }
}
