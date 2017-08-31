package br.com.dalcim.picpay.payment;

import br.com.dalcim.picpay.data.Payment;
import br.com.dalcim.picpay.data.Transaction;
import br.com.dalcim.picpay.data.local.RepositoryLocal;
import br.com.dalcim.picpay.data.remote.RepositoryRemote;

/**
 * @author Wiliam
 * @since 27/08/2017
 */

public class PaymentPresenter implements PaymentContract.Presenter {

    private final PaymentContract.View view;
    private final RepositoryRemote repositoryRemote;
    private final RepositoryLocal repositoryLocal;

    public PaymentPresenter(PaymentContract.View view, RepositoryRemote repositoryRemote, RepositoryLocal repositoryLocal) {
        this.view = view;
        this.repositoryRemote = repositoryRemote;
        this.repositoryLocal = repositoryLocal;
    }

    @Override
    public void loadCreditCards() {
        view.loadCards(repositoryLocal.getCreditCards());
    }

    @Override
    public void send(Payment payment) {
        repositoryRemote.transaction(payment, new RepositoryRemote.TransactionCallback() {
            @Override
            public void onSucess(Transaction transaction) {
                view.paymentOnSucess(transaction);
            }

            @Override
            public void notApproved(Transaction transaction) {
                view.paymentNotApproved(transaction);
            }

            @Override
            public void onFailure(String failure) {
                view.paymentOnFailure(failure);
            }
        });
    }
}
