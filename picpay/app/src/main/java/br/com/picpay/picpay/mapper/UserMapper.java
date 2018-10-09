package br.com.picpay.picpay.mapper;

import android.support.annotation.NonNull;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;

import br.com.picpay.core.modelResponse.UserResponse;
import br.com.picpay.picpay.model.User;

@EBean(scope = EBean.Scope.Singleton)
public class UserMapper {

    public User responseToUser(UserResponse response) {
        User user = new User();
        user.setId(response.getId());
        user.setName(response.getName());
        user.setImg(response.getImg());
        user.setUsername(response.getUserName());
        return user;
    }

    public ArrayList<User> responseToUser(@NonNull ArrayList<UserResponse> response) {
        ArrayList<User> users = new ArrayList<>();
        for (UserResponse userResponse : response) {
            users.add(responseToUser(userResponse));
        }
        return users;
    }
}
