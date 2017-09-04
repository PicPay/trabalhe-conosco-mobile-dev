package br.com.dalcim.picpay.creditcard;

import br.com.dalcim.picpay.data.CreditCard;

/**
 * @author Wiliam
 * @since 29/08/2017
 */

public interface CreditCardContract {
    interface View{
        void onSucessSave(CreditCard creditCard);
        void showError(String error);
    }

    interface Presenter{
        void save(String numberCreditCard, String expiryDate, String cvv);
    }
}
