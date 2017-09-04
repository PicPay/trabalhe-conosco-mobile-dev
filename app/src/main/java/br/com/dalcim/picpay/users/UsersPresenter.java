package br.com.dalcim.picpay.users;

import java.util.List;

import br.com.dalcim.picpay.data.User;
import br.com.dalcim.picpay.data.remote.RepositoryRemote;

public class UsersPresenter implements UsersContract.Presenter {

    private final UsersContract.View view;
    private final RepositoryRemote repositoryRemote;

    public UsersPresenter(UsersContract.View view, RepositoryRemote repositoryRemote) {
        this.view = view;
        this.repositoryRemote = repositoryRemote;
    }

    @Override
    public void getUsers() {
        view.setLoadingIndicator(true);
        repositoryRemote.getUsers(new RepositoryRemote.GetUsersCallback() {
            @Override
            public void onSucess(List<User> users) {
                view.loadUsers(users);
                view.setLoadingIndicator(false);
            }

            @Override
            public void emptyList() {
                view.showNoUsers();
                view.setLoadingIndicator(false);
            }

            @Override
            public void onFailure(String failure) {
                view.showFailureLoadUsers(failure);
                view.setLoadingIndicator(false);
            }
        });
    }

    @Override
    public void userSelected(User user) {
        view.showPaymentActivity(user);
    }
}
