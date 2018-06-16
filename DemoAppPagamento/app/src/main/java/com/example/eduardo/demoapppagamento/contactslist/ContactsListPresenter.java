package com.example.eduardo.demoapppagamento.contactslist;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.eduardo.demoapppagamento.data.Contact;
import com.example.eduardo.demoapppagamento.data.source.ContactsDataSource;
import com.example.eduardo.demoapppagamento.data.source.ContactsRepository;

import java.util.List;

public class ContactsListPresenter implements ContactsListContract.Presenter {


    //private final ContactsRepository mContactsRepository;


    /*
    private final TasksContract.View mTasksView;

    private TasksFilterType mCurrentFiltering = TasksFilterType.ALL_TASKS;

    private boolean mFirstLoad = true;

    public TasksPresenter(@NonNull TasksRepository tasksRepository, @NonNull TasksContract.View tasksView) {
        mTasksRepository = checkNotNull(tasksRepository, "tasksRepository cannot be null");
        mTasksView = checkNotNull(tasksView, "tasksView cannot be null!");

        mTasksView.setPresenter(this);
    }
    */
    private ContactsRepository mContactsRepository;
    private ContactsListContract.View mContactsView;

    public ContactsListPresenter(@NonNull ContactsRepository repository, @NonNull ContactsListContract.View view) {
        mContactsRepository = repository;
        mContactsView = view;

        mContactsView.setPresenter(this);
        start();
    }

    @Override
    public void start() {
        loadContacts();
    }

    private void loadContacts() {
        mContactsRepository.getContacts(new ContactsDataSource.LoadContactsCallback() {
            @Override
            public void onContactsLoaded(List<Contact> contacts) {
                mContactsView.showContacts(contacts);
            }

            @Override
            public void onDataNotAvailable() {
                Log.d("TODO", "onDataNotAvailable");
            }
        });
    }


}
