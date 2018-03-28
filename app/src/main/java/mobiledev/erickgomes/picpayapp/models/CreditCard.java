package mobiledev.erickgomes.picpayapp.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by erickgomes on 23/03/2018.
 */

@Entity(tableName = "creditcards")
public class CreditCard {
    private String card_owner;
    private String card_number;
    private String expiry_date;
    @PrimaryKey(autoGenerate = true)
    private int cvv;
    private int brand;
    private boolean isChecked;

    public CreditCard() {
        this.isChecked = false;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getCard_number() {
        return card_number;
    }


    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public int getBrand() {
        return brand;
    }

    public void setBrand(int brand) {
        this.brand = brand;
    }

    public String getCard_owner() {
        return card_owner;
    }

    public void setCard_owner(String card_owner) {
        this.card_owner = card_owner;
    }
}
