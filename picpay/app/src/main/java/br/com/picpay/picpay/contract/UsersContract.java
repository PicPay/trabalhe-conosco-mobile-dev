package br.com.picpay.picpay.contract;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import br.com.picpay.picpay.model.User;

public class UsersContract {

    public interface UsersView {
        void onErrorUsers(@NonNull String men);

        void onUsers(@NonNull ArrayList<User> response);
    }

    public interface UsersPresenter {
        void getUsers();
    }
}
