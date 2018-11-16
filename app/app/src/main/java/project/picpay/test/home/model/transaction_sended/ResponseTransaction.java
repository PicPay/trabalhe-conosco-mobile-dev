package project.picpay.test.home.model.transaction_sended;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rodrigo Oliveira on 13/11/2018 for app.
 * Contact us rodrigooliveira.tecinfo@gmail.com
 */
public class ResponseTransaction {

    @SerializedName("transaction")
    @Expose
    private Transaction transaction;

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

}