package br.com.picpay.picpay.model;

import java.io.Serializable;

public class ChangeCard implements Serializable {

    private Card card;

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
