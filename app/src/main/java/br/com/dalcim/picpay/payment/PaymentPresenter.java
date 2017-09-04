package br.com.dalcim.picpay.payment;

import java.util.List;

import br.com.dalcim.picpay.data.CreditCard;
import br.com.dalcim.picpay.data.Payment;
import br.com.dalcim.picpay.data.Transaction;
import br.com.dalcim.picpay.data.local.RepositoryLocal;
import br.com.dalcim.picpay.data.remote.RepositoryRemote;

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
        List<CreditCard> cards = repositoryLocal.getCreditCards();
        if(cards.isEmpty()){
            view.showNoCards();
        }else{
            view.loadCards(cards);
        }
    }

    @Override
    public void send(CreditCard creditCard, double value, long id) {
        if(creditCard == null){
            view.showError("Informe um cartão de Crédito Válido!");
        }else if(value == 0){
            view.showError("Informe um valor válido!");
        }else{
            Payment payment = new Payment(id, creditCard, value);
            repositoryRemote.transaction(payment, new RepositoryRemote.TransactionCallback() {
                @Override
                public void onSucess(Transaction transaction) {
                    view.paymentOnSucess(transaction);
                }

                @Override
                public void notApproved(Transaction transaction) {
                    view.showError("O Pagamento não foi aprovado!");
                }

                @Override
                public void onFailure(String failure) {
                    view.showError(failure);
                }
            });
        }
    }
}
