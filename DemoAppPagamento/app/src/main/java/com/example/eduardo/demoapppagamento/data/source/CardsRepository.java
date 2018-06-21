package com.example.eduardo.demoapppagamento.data.source;

import android.support.annotation.NonNull;

import com.example.eduardo.demoapppagamento.data.Card;

public class CardsRepository implements  CardsDataSource {

    private static CardsRepository INSTANCE = null;

    private final CardsDataSource mCardsLocalDataSource;

    public CardsRepository (CardsDataSource cardsLocalDataSource) {
        mCardsLocalDataSource = cardsLocalDataSource;
    }

    public static CardsRepository getInstance(CardsDataSource cardsLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new CardsRepository(cardsLocalDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getCards(@NonNull LoadCardsCallback callback) {
        //checkNotNull(callback);
        mCardsLocalDataSource.getCards(callback);
    }

    @Override
    public void saveCard(@NonNull Card card) {
        mCardsLocalDataSource.saveCard(card);
    }

    @Override
    public void deleteCard(@NonNull Card card) {
        mCardsLocalDataSource.deleteCard(card);
    }
}
