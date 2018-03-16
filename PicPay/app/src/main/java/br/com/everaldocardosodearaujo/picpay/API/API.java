package br.com.everaldocardosodearaujo.picpay.API;

import java.util.List;

import br.com.everaldocardosodearaujo.picpay.Object.UserObject;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by E. Cardoso de Araújo on 15/03/2018.
 */

public class API {
    private static final Retrofit RETROFIT = new Retrofit
            .Builder()
            .baseUrl("http://careers.picpay.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static  Call<List<UserObject>> getUsers() {
        UsersAPI usersAPI = RETROFIT.create(UsersAPI.class);
        return usersAPI.getUsers();
    }
}
