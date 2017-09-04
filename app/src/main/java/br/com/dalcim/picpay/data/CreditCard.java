package br.com.dalcim.picpay.data;

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

    public String getMaskNumber() {
        return "**** " + cardNumber.substring(cardNumber.length() - 4, cardNumber.length());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreditCard that = (CreditCard) o;

        return cardNumber.equals(that.cardNumber);

    }
}
