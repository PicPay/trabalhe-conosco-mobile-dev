package br.com.dalcim.picpay.users;

import java.util.List;

import br.com.dalcim.picpay.data.User;

public interface UsersContract {
    interface View{
        void loadUsers(List<User> users);
        void setLoadingIndicator(boolean active);
        void showNoUsers();
        void showFailureLoadUsers(String failure);
        void showPaymentActivity(User user);
    }

    interface Presenter{
        void getUsers();
        void userSelected(User user);
    }
}
