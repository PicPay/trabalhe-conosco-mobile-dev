package br.com.gsas.app.picpay.Domain;

import com.google.gson.annotations.SerializedName;

public class Transaction {

    @SerializedName("transaction")
    private Feed transaction;

    public Feed getTransaction() {
        return transaction;
    }

    public void setTransaction(Feed transaction) {
        this.transaction = transaction;
    }
}
