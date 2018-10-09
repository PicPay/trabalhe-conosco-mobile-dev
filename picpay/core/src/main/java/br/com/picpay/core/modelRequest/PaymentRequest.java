package br.com.picpay.core.modelRequest;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import br.com.picpay.core.util.StringUtil;

public class PaymentRequest {

    @Expose
    @SerializedName("card_number")
    private String carNumber;

    @Expose
    @SerializedName("cvv")
    private String cvv;

    @Expose
    @SerializedName("value")
    private Float value;

    @Expose
    @SerializedName("expiry_date")
    private String expiryDate;

    @Expose
    @SerializedName("destination_user_id")
    private String destinationUserId;

    public PaymentRequest(@NonNull String carNumber, @NonNull String cvv, @NonNull Float value, @NonNull String expiryDate, @NonNull String destinationUserId) {
        this.carNumber = carNumber;
        this.cvv = cvv;
        this.value = value;
        this.expiryDate = expiryDate;
        this.destinationUserId = destinationUserId;
    }

    @NonNull
    public String getCarNumber() {
        return StringUtil.fixString(carNumber);
    }

    @NonNull
    public String getCvv() {
        return StringUtil.fixString(cvv);
    }

    @NonNull
    public Float getValue() {
        return value == null ? 0 : value;
    }

    @NonNull
    public String getExpiryDate() {
        return StringUtil.fixString(expiryDate);
    }

    @NonNull
    public String getDestinationUserId() {
        return StringUtil.fixString(destinationUserId);
    }
}
