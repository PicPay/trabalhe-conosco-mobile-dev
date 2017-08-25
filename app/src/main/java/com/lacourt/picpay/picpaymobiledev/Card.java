package com.lacourt.picpay.picpaymobiledev;

/**
 * Created by igor on 17/08/2017.
 */

public class Card {
//    private int userID;
    private String cardNumber;
    private Integer cvv;
    private String expiryDate;

    public Card() {
    }

    public Card(String cardNumber, int cvv, String expiryDate){
//        this.userID = userID;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
    }

//    public int getUserID() {
//        return userID;
//    }
//
//    public void setUserID(int userID) {
//        this.userID = userID;
//    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
