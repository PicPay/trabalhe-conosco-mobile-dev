package com.example.eduardo.demoapppagamento.data.source;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.eduardo.demoapppagamento.data.source.local.CardsDatabase;
import com.example.eduardo.demoapppagamento.data.source.local.CardsLocalDataSource;
import com.example.eduardo.demoapppagamento.data.source.remote.ContactsRemoteDataSource;
import com.example.eduardo.demoapppagamento.util.AppExecutors;

public class RepositoryInjection {

    public static ContactsRepository provideContactsRepository(@NonNull Context context) {
        //checkNotNull(context);
        return ContactsRepository.getInstance(ContactsRemoteDataSource.getInstance());
    }

    public static CardsRepository provideCardsRepository(@NonNull Context context) {
        //checkNotNull(context);
        CardsDatabase database = CardsDatabase.getInstance(context);
        //return TasksRepository.getInstance(FakeTasksRemoteDataSource.getInstance(),
         //       TasksLocalDataSource.getInstance(new AppExecutors(),
        //database.taskDao()));
        return CardsRepository.getInstance(CardsLocalDataSource.getInstance(new AppExecutors(),database.cardsDao()));
    }

}
