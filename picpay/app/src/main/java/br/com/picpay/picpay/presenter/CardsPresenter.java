package br.com.picpay.picpay.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import br.com.picpay.picpay.R;
import br.com.picpay.picpay.contract.CardsContract;
import br.com.picpay.picpay.listerner.ResponseViewListerner;
import br.com.picpay.picpay.model.Card;
import br.com.picpay.picpay.model.Transaction;
import br.com.picpay.picpay.model.User;
import br.com.picpay.picpay.usecase.payments.PostPaymentCase;
import io.realm.Realm;
import io.realm.RealmResults;

@EBean
public class CardsPresenter extends Presenter<CardsContract.CardsView> implements CardsContract.CardsPresenter {

    @RootContext
    Context context;

    @Bean
    PostPaymentCase postPaymentCase;

    @Override
    public void deleteCard(final @NonNull Card card) {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                RealmResults<Card> result = realm.where(Card.class).equalTo("id", card.getId()).findAll();
                result.deleteAllFromRealm();
                getView().onCardDeleted(card);
            }
        });
    }

    @Override
    public void executeTransaction(@NonNull User user, @NonNull Card card, float value) {
        postPaymentCase.sendPaymeny(user, card, value, new ResponseViewListerner<Transaction>() {
            @Override
            public void success(@NonNull Transaction response) {
                if (response.isSuccess()) {
                    getView().onSucessTransaction();
                } else {
                    getView().onErrorTransaction(context.getString(R.string.refused_transaction));
                }
            }

            @Override
            public void error(@NonNull String men) {
                getView().onErrorTransaction(men);
            }
        });

    }
}