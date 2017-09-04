package br.com.dalcim.picpay.creditcard;

import br.com.dalcim.picpay.data.CreditCard;

/**
 * @author Wiliam
 * @since 29/08/2017
 */

public interface CreditCardContract {
    interface View{
        void onInvalidNumber();
        void onInvalidExpiryDate();
        void onInvalidCvv();
        void onFailure();
        void onSucessSave(CreditCard creditCard);
    }

    interface Presenter{
        void save(String numberCreditCard, String expiryDate, String cvv);
    }
}
