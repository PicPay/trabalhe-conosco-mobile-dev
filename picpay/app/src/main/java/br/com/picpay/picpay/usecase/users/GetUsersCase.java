package br.com.picpay.picpay.usecase.users;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;

import br.com.picpay.core.api.user.UserApi;
import br.com.picpay.core.listerner.ResponseServer;
import br.com.picpay.core.modelResponse.ErrorResponse;
import br.com.picpay.core.modelResponse.UserResponse;
import br.com.picpay.picpay.listerner.ResponseViewListerner;
import br.com.picpay.picpay.mapper.UserMapper;
import br.com.picpay.picpay.model.User;

@EBean(scope = EBean.Scope.Singleton)
public class GetUsersCase {

    @Bean
    UserApi userApi;

    @Bean
    UserMapper userMapper;

    public void getUsers(final ResponseViewListerner<ArrayList<User>> listerner) {
        userApi.getUsers(new ResponseServer<ArrayList<UserResponse>>() {
            @Override
            public void success(ArrayList<UserResponse> response) {
                listerner.success(userMapper.responseToUser(response));
            }

            @Override
            public void error(ErrorResponse errorResponse) {
                listerner.error(errorResponse.getMen());
            }
        });
    }
}
