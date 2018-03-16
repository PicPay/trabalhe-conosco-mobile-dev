package br.com.everaldocardosodearaujo.picpay.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import br.com.everaldocardosodearaujo.picpay.API.API;
import br.com.everaldocardosodearaujo.picpay.Adapter.UsersAdapter;
import br.com.everaldocardosodearaujo.picpay.App.Functions;
import br.com.everaldocardosodearaujo.picpay.Object.UserObject;
import br.com.everaldocardosodearaujo.picpay.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersActivity extends Activity implements Functions.RecyclerViewTouchListener.RecyclerViewOnClickListenerHack {
    private RecyclerView idRvUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        this.inflate();
        this.getUsersFromApi();
    }

    private void inflate(){
        this.idRvUsers = (RecyclerView) findViewById(R.id.idRvUsers);
        this.idRvUsers.setHasFixedSize(true);
        this.idRvUsers.addOnItemTouchListener(new Functions.RecyclerViewTouchListener(UsersActivity.this, this.idRvUsers, this));
        LinearLayoutManager llm = new LinearLayoutManager(UsersActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        this.idRvUsers.setLayoutManager(llm);
    }

    private void getUsersFromApi(){
        API.getUsers()
                .enqueue(new Callback<List<UserObject>>() {
                    @Override
                    public void onResponse(Call<List<UserObject>> call, Response<List<UserObject>> response) {
                        if (response.isSuccessful()){
                            UsersAdapter adapter = new UsersAdapter(UsersActivity.this,response.body());
                            idRvUsers.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<UserObject>> call, Throwable t) {

                    }
                });
    }

    @Override
    public void onClickListener(View view, int position) {

    }

    @Override
    public void onLongPressClickListener(View view, int position) {

    }
}
