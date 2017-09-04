package br.com.dalcim.picpay.data.local;

import android.content.Context;
import java.util.List;
import br.com.dalcim.picpay.data.CreditCard;


public class RepositoryLocaImpl implements RepositoryLocal {

    private final CreditCardDao creditCardDao;

    public RepositoryLocaImpl(Context context) {
        this.creditCardDao = new CreditCardDao(context);
    }

    @Override
    public List<CreditCard> getCreditCards() {
        return creditCardDao.getList();
    }

    @Override
    public void saveCreditCard(CreditCard creditCard, CreditCardSaveCallBack callBack) {
        if(creditCardDao.insert(creditCard)){
            callBack.onSucess(creditCard);
        }else {
            callBack.onFailure();
        }
    }
}
