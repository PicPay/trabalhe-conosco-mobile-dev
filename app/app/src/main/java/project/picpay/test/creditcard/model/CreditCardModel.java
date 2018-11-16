package project.picpay.test.creditcard.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by Rodrigo Oliveira on 15/11/2018 for app.
 * Contact us rodrigooliveira.tecinfo@gmail.com
 */
@Entity(tableName = "credit_card")
public class CreditCardModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;
    private String cardNumber;
    private String expiredDate;
    private String cardHolder;
    private String cvvCode;

    public CreditCardModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getCvvCode() {
        return cvvCode;
    }

    public void setCvvCode(String cvvCode) {
        this.cvvCode = cvvCode;
    }

    @Override
    public String toString() {
        return "Card info:\r\n" +
                "Card number = " + cardNumber + "\r\n" +
                "Expired date = " + expiredDate + "\r\n" +
                "Card holder = " + cardHolder + "\r\n" +
                "CVV code = " + cvvCode;
    }
}
