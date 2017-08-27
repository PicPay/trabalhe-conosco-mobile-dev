package br.com.dalcim.picpay.data;

/**
 * @author Wiliam
 * @since 25/08/2017
 */
public class TransactionResponse {
    private Transaction transaction;

    public TransactionResponse(Transaction transaction) {
        this.transaction = transaction;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
