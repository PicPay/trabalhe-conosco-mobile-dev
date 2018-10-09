package br.com.picpay.picpay.contract;

import android.support.annotation.NonNull;

import br.com.picpay.picpay.model.Card;
import br.com.picpay.picpay.model.User;

public class CardsContract {

    public interface CardsView {
        void onCardDeleted(@NonNull Card card);

        void onSucessTransaction();

        void onErrorTransaction(String error);
    }

    public interface CardsPresenter {
        void deleteCard(@NonNull Card card);

        void executeTransaction(@NonNull User user, @NonNull Card card, float value);
    }
}
