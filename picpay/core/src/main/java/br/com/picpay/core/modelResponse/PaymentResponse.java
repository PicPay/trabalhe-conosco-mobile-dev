package br.com.picpay.core.modelResponse;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class PaymentResponse {

    @SerializedName("transaction")
    private TransactionResponse transactionResponse;

    @NonNull
    public TransactionResponse getTransactionResponse() {
        return transactionResponse == null ? new TransactionResponse() : transactionResponse;
    }
}
