package br.com.dalcim.picpay.data;

/**
 * @author Wiliam
 * @since 29/08/2017
 */

public class CreditCard {
    private String cardNumber;
    private String expiryDate;
    private int cvv;

    public CreditCard() {
    }

    public CreditCard(String cardNumber, String expiryDate, int cvv) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
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

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
