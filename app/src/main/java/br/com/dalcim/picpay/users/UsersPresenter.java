package br.com.dalcim.picpay.users;

import br.com.dalcim.picpay.data.User;
import br.com.dalcim.picpay.data.remote.RepositoryRemote;

/**
 * @author Wiliam
 * @since 27/08/2017
 */
public class UsersPresenter implements UsersContract.Presenter {

    private final UsersContract.View view;
    private final RepositoryRemote repositoryRemote;

    public UsersPresenter(UsersContract.View view, RepositoryRemote repositoryRemote) {
        this.view = view;
        this.repositoryRemote = repositoryRemote;
    }

    @Override
    public void getUsers() {
    }

    @Override
    public void userSelected(User user) {
    }
}
