package br.com.picpay.picpay.contract;

import android.support.annotation.NonNull;

import br.com.picpay.picpay.model.Card;

public class AddCardContract {

    public interface AddCardView {
        void onCardSaved(@NonNull Card card);
    }

    public interface AddCardPresenter {
        void saveCard(@NonNull Card card);
    }
}
