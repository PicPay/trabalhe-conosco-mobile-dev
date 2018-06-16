package com.example.eduardo.demoapppagamento.data.source;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.eduardo.demoapppagamento.data.source.remote.ContactsRemoteDataSource;

public class RepositoryInjection {

    public static ContactsRepository provideContactsRepository(@NonNull Context context) {
        //checkNotNull(context);
        return ContactsRepository.getInstance(ContactsRemoteDataSource.getInstance());
    }

}
