package br.com.everaldocardosodearaujo.picpay.Activity;

import android.app.Activity;
import android.os.Bundle;

import java.util.List;

import br.com.everaldocardosodearaujo.picpay.API.API;
import br.com.everaldocardosodearaujo.picpay.Object.UsersObject;
import br.com.everaldocardosodearaujo.picpay.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersActivity extends Activity {

    private List<UsersObject> lstUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        getUsersFromApi();
    }

    private void getUsersFromApi(){
        API.getUsers()
                .enqueue(new Callback<List<UsersObject>>() {
                    @Override
                    public void onResponse(Call<List<UsersObject>> call, Response<List<UsersObject>> response) {
                        if (response.isSuccessful()){
                            lstUsers = response.body();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<UsersObject>> call, Throwable t) {

                    }
                });
    }
}
