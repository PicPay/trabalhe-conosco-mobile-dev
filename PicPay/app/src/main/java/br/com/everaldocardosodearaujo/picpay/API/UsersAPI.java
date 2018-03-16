package br.com.everaldocardosodearaujo.picpay.API;

import java.util.List;

import br.com.everaldocardosodearaujo.picpay.Object.UserObject;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by E. Cardoso de Araújo on 15/03/2018.
 */

public interface UsersAPI {
    @GET("tests/mobdev/users")
    Call<List<UserObject>> getUsers();
}

