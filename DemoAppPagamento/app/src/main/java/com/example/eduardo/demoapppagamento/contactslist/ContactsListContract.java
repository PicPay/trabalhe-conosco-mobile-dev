package com.example.eduardo.demoapppagamento.contactslist;

import android.support.annotation.NonNull;

import com.example.eduardo.demoapppagamento.BasePresenter;
import com.example.eduardo.demoapppagamento.BaseView;
import com.example.eduardo.demoapppagamento.data.Contact;

import java.util.List;

public interface ContactsListContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showContacts(List<Contact> contacts);

        /*
        void showAddTask();

        void showTaskDetailsUi(String taskId);

        void showTaskMarkedComplete();

        void showTaskMarkedActive();

        void showCompletedTasksCleared();

        void showLoadingTasksError();

        void showNoTasks();

        void showActiveFilterLabel();

        void showCompletedFilterLabel();

        void showAllFilterLabel();

        void showNoActiveTasks();

        void showNoCompletedTasks();

        void showSuccessfullySavedMessage();

        boolean isActive();

        void showFilteringPopUpMenu();
        */
    }

    interface Presenter extends BasePresenter {

        /*
        void result(int requestCode, int resultCode);

        void loadTasks(boolean forceUpdate);

        void addNewTask();

        void openTaskDetails(@NonNull Task requestedTask);

        void completeTask(@NonNull Task completedTask);

        void activateTask(@NonNull Task activeTask);

        void clearCompletedTasks();

        void setFiltering(TasksFilterType requestType);

        TasksFilterType getFiltering();
        */
    }
}
