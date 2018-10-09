package br.com.picpay.core.api.user;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import br.com.picpay.core.listerner.ResponseServer;
import br.com.picpay.core.modelResponse.UserResponse;

public interface IUserApi {

    void getUsers(@NonNull final ResponseServer<ArrayList<UserResponse>> listerner);
}
