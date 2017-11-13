package br.com.devmarques.picpaytestes.Dados;

/**
 * Created by Roger on 09/11/2017.
 */

public class Cartao {

    private long id;
    private String nomeCard;
    private String CardNumber;
    private String cvv;
    private String ExpirationDate;

    public Cartao(String nomeCard,String cardNumber, String cvv, String expirationDate) {
        this.nomeCard=nomeCard;
        CardNumber = cardNumber;
        this.cvv = cvv;
        ExpirationDate = expirationDate;
    }

    public Cartao(long id, String nomeCard, String cardNumber, String cvv, String expirationDate) {
        this.id = id;
        this.nomeCard = nomeCard;
        CardNumber = cardNumber;
        this.cvv = cvv;
        ExpirationDate = expirationDate;
    }

    public Cartao() {
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getExpirationDate() {
        return ExpirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        ExpirationDate = expirationDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomeCard() {
        return nomeCard;
    }

    public void setNomeCard(String nomeCard) {
        this.nomeCard = nomeCard;
    }
}
