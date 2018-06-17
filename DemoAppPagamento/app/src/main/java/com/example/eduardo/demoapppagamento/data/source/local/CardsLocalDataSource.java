package com.example.eduardo.demoapppagamento.data.source.local;

import android.support.annotation.NonNull;

import com.example.eduardo.demoapppagamento.data.Card;
import com.example.eduardo.demoapppagamento.data.source.CardsDataSource;
import com.example.eduardo.demoapppagamento.util.AppExecutors;

import java.util.List;

public class CardsLocalDataSource implements CardsDataSource {

    private static volatile CardsLocalDataSource INSTANCE;
    private CardsDao mCardsDao;
    private AppExecutors mAppExecutors;

    private CardsLocalDataSource(@NonNull AppExecutors appExecutors,
                                 @NonNull CardsDao cardsDao) {
        mAppExecutors = appExecutors;
        mCardsDao = cardsDao;
    }

    public static CardsLocalDataSource getInstance(@NonNull AppExecutors appExecutors,
                                                   @NonNull CardsDao cardsDao) {
        if (INSTANCE == null) {
            synchronized (CardsLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CardsLocalDataSource(appExecutors, cardsDao);
                }
            }
        }
        return INSTANCE;
    }

    /**
    * Note: {@link LoadCardsCallback#onDataNotAvailable()} is fired if the database doesn't exist
    * or the table is empty.
     */
    @Override
    public void getCards(@NonNull final LoadCardsCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Card> cards = mCardsDao.loadAllCards();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (cards.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
                            callback.onCardsLoaded(cards);
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void saveCard(@NonNull final Card card) {
        //checkNotNull(task);
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mCardsDao.insertCard(card);
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);
    }

    @Override
    public void deleteCard(@NonNull final Card card) {
       Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                mCardsDao.deleteCard(card);
            }
        };
        mAppExecutors.diskIO().execute(deleteRunnable);
    }
}
