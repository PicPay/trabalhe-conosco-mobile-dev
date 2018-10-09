package br.com.picpay.picpay.presenter;

import android.support.annotation.NonNull;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;

import br.com.picpay.picpay.contract.UsersContract;
import br.com.picpay.picpay.listerner.ResponseViewListerner;
import br.com.picpay.picpay.model.User;
import br.com.picpay.picpay.usecase.users.GetUsersCase;

@EBean
public class UsersPresenter extends Presenter<UsersContract.UsersView> implements UsersContract.UsersPresenter {

    @Bean
    GetUsersCase getUsersCase;

    @Override
    public void getUsers() {
        getUsersCase.getUsers(new ResponseViewListerner<ArrayList<User>>() {
            @Override
            public void success(@NonNull ArrayList<User> response) {
                getView().onUsers(response);
            }

            @Override
            public void error(@NonNull String men) {
                getView().onErrorUsers(men);
            }
        });
    }
}
