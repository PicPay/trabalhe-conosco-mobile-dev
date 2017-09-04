package br.com.dalcim.picpay.data.local;

import java.util.List;

import br.com.dalcim.picpay.data.CreditCard;

public interface RepositoryLocal {
    List<CreditCard> getCreditCards();
    void saveCreditCard(CreditCard creditCard, CreditCardSaveCallBack callBack);


    interface CreditCardSaveCallBack{
        void onSucess(CreditCard creditCard);
        void onFailure();
    }
}
