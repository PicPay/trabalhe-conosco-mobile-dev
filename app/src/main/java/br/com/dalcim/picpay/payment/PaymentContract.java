package br.com.dalcim.picpay.payment;

import java.util.List;

import br.com.dalcim.picpay.data.CreditCard;
import br.com.dalcim.picpay.data.Transaction;

/**
 * @author Wiliam
 * @since 27/08/2017
 */

public interface PaymentContract {
    interface View{
        void loadCards(List<CreditCard> creditCards);
        void showNoCards();
        void paymentOnSucess(Transaction transaction);
        void showError(String failure);
    }

    interface Presenter{
        void loadCreditCards();
        void send(CreditCard creditCard, double value, long id);
    }
}
