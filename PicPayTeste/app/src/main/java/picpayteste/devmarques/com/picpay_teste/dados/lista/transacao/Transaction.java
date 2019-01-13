package picpayteste.devmarques.com.picpay_teste.dados.lista.transacao;

import java.io.Serializable;

public class Transaction implements Serializable {

    public static final String PARAM_TRANSACTION = "PARAM_TRANSACTION";

    private Transacoes transaction;

    public Transaction() {
    }

    public Transaction(Transacoes transaction) {
        this.transaction = transaction;
    }

    public Transacoes getTransaction() {
        return transaction;
    }

    public void setTransaction(Transacoes transaction) {
        this.transaction = transaction;
    }


}
