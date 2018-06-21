package com.example.eduardo.demoapppagamento.data.source;

import android.support.annotation.NonNull;

import com.example.eduardo.demoapppagamento.data.Contact;
import java.util.List;

public interface ContactsDataSource {

    interface LoadContactsCallback {

        void onContactsLoaded(List<Contact> contacts);

        void onDataNotAvailable();
    }

    void getContacts(@NonNull LoadContactsCallback callback);
}
