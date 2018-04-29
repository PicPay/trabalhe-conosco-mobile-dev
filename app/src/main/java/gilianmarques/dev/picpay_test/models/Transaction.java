package gilianmarques.dev.picpay_test.models;

import java.io.Serializable;

public class Transaction implements Serializable {

    private long date;
    private CreditCard card;
    private Contact contact;
    private float amount;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public CreditCard getCard() {
        return card;
    }

    public void setCard(CreditCard card) {
        this.card = card;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getAmount() {
        return amount;
    }
}