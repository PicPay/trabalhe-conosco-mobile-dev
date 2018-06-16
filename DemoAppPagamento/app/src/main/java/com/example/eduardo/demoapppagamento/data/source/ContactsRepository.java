package com.example.eduardo.demoapppagamento.data.source;

import android.support.annotation.NonNull;

import com.example.eduardo.demoapppagamento.data.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactsRepository implements ContactsDataSource {

    private static ContactsRepository INSTANCE = null;
    private final ContactsDataSource mContactsRemoteDataSource;

    private ContactsRepository (@NonNull ContactsDataSource contactsRemoteDataSource) {
        //mContactsRemoteDataSource = checkNotNull(contactsRemoteDataSource);
        mContactsRemoteDataSource = contactsRemoteDataSource;
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param contactsRemoteDataSource the backend data source
     * @return the {@link ContactsRepository} instance
     */
    public static ContactsRepository getInstance(ContactsDataSource contactsRemoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new ContactsRepository(contactsRemoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getContacts(@NonNull final LoadContactsCallback callback) {
        //checkNotNull(callback);
        getTasksFromRemoteDataSource(callback);
    }

    private void getTasksFromRemoteDataSource(@NonNull final LoadContactsCallback callback) {
        mContactsRemoteDataSource.getContacts(callback);
    }

}
