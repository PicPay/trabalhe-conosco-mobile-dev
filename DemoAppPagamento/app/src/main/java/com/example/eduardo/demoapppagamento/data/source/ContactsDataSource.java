package com.example.eduardo.demoapppagamento.data.source;

import android.support.annotation.NonNull;

import com.example.eduardo.demoapppagamento.data.Contact;
import java.util.List;

public interface ContactsDataSource {

    interface LoadContactsCallback {

        void onContactsLoaded(List<Contact> contacts);

        void onDataNotAvailable();
    }

    /*interface GetTaskCallback {

        void onTaskLoaded(Task task);

        void onDataNotAvailable();
    }*/

    void getContacts(@NonNull LoadContactsCallback callback);

    /*void getTask(@NonNull String taskId, @NonNull GetTaskCallback callback);

    void saveTask(@NonNull Task task);

    void completeTask(@NonNull Task task);

    void completeTask(@NonNull String taskId);

    void activateTask(@NonNull Task task);

    void activateTask(@NonNull String taskId);

    void clearCompletedTasks();

    void refreshTasks();

    void deleteAllTasks();

    void deleteTask(@NonNull String taskId);*/
}
