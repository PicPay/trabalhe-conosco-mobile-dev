package project.picpay.test.home.model.transaction_received;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rodrigo Oliveira on 14/11/2018 for app.
 * Contact us rodrigooliveira.tecinfo@gmail.com
 */
public class TransactionPost {

    @SerializedName("card_number")
    @Expose
    private String cardNumber;
    @SerializedName("cvv")
    @Expose
    private Integer cvv;
    @SerializedName("value")
    @Expose
    private Double value;
    @SerializedName("expiry_date")
    @Expose
    private String expiryDate;
    @SerializedName("destination_user_id")
    @Expose
    private Integer destinationUserId;

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

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getDestinationUserId() {
        return destinationUserId;
    }

    public void setDestinationUserId(Integer destinationUserId) {
        this.destinationUserId = destinationUserId;
    }

}
