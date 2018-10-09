package br.com.picpay.core.api.user;

import java.util.ArrayList;

import br.com.picpay.core.modelResponse.UserResponse;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;

public interface UserRetrofit {

    @GET("users")
    Observable<Response<ArrayList<UserResponse>>> getUsers();
}
