package com.example.eduardo.demoapppagamento.data.source;

import android.support.annotation.NonNull;

import com.example.eduardo.demoapppagamento.data.Card;
import com.example.eduardo.demoapppagamento.data.Contact;

import java.util.List;

public interface CardsDataSource {

    interface LoadCardsCallback {

        void onCardsLoaded(List<Card> cards);

        void onDataNotAvailable();
    }

    void getCards(@NonNull CardsDataSource.LoadCardsCallback callback);

    void saveCard(@NonNull Card card);

    void deleteCard(@NonNull Card card);
}
