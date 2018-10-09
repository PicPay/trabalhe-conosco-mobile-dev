package br.com.picpay.picpay.presenter;

import android.support.annotation.NonNull;

import org.androidannotations.annotations.EBean;

import br.com.picpay.picpay.contract.AddCardContract;
import br.com.picpay.picpay.model.Card;
import io.realm.Realm;

@EBean
public class AddCardPresenter extends Presenter<AddCardContract.AddCardView> implements AddCardContract.AddCardPresenter {

    @Override
    public void saveCard(@NonNull final Card card) {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.copyToRealmOrUpdate(card);
                getView().onCardSaved(card);
            }
        });
    }
}
