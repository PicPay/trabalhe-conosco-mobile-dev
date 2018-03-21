package br.com.everaldocardosodearaujo.picpay.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import br.com.everaldocardosodearaujo.picpay.API.API;
import br.com.everaldocardosodearaujo.picpay.Adapter.UsersAdapter;
import br.com.everaldocardosodearaujo.picpay.App.FunctionsApp;
import br.com.everaldocardosodearaujo.picpay.Object.UserObject;
import br.com.everaldocardosodearaujo.picpay.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.everaldocardosodearaujo.picpay.App.FunctionsApp.closePgDialog;
import static br.com.everaldocardosodearaujo.picpay.App.FunctionsApp.showPgDialog;

public class UsersActivity extends Activity
        implements FunctionsApp.RecyclerViewTouchListener.RecyclerViewOnClickListenerHack,
        NavigationView.OnNavigationItemSelectedListener,
        LoaderManager.LoaderCallbacks<Cursor> ,
        SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView idRvUsers;
    private SwipeRefreshLayout idScUsers;
    private CoordinatorLayout idClRefresh;
    private Button idBtnRefresh;
    private LinearLayout idLLUsers;
    private LinearLayout idLLListUsers;
    private ProgressDialog pgLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        this.inflate();
        showPgDialog(UsersActivity.this);
        this.getUsersFromApi();
    }

    private void inflate(){
        this.idClRefresh = (CoordinatorLayout) findViewById(R.id.idClRefresh);

        this.idBtnRefresh = (Button) findViewById(R.id.idBtnRefresh);

        this.idLLUsers = (LinearLayout) findViewById(R.id.idLLUsers);

        this.idLLListUsers = (LinearLayout) findViewById(R.id.idLLListUsers);

        this.idScUsers = (SwipeRefreshLayout) findViewById(R.id.idScUsers);
        this.idScUsers.setOnRefreshListener(this);

        this.idRvUsers = (RecyclerView) findViewById(R.id.idRvUsers);
        this.idRvUsers.setHasFixedSize(true);
        this.idRvUsers.addOnItemTouchListener(new FunctionsApp.RecyclerViewTouchListener(UsersActivity.this, this.idRvUsers, this));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UsersActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.idRvUsers.setLayoutManager(linearLayoutManager);
    }

    private void getUsersFromApi(){
        API.getUsers()
                .enqueue(new Callback<List<UserObject>>() {
                    @Override
                    public void onResponse(Call<List<UserObject>> call, Response<List<UserObject>> response) {
                        if (response.isSuccessful()){
                            idRvUsers.setAdapter(new UsersAdapter(UsersActivity.this,response.body()));
                            idScUsers.setRefreshing(false);
                            try{idLLUsers.addView(idLLListUsers);}catch(Exception ex){}
                            try{idLLUsers.removeView(idClRefresh);}catch (Exception ex){}
                            closePgDialog();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<UserObject>> call, Throwable t) {
                        Toast.makeText(UsersActivity.this,
                                "Ocorreu um erro: " + t.getMessage() +". Causa: " + t.getCause(),
                                Toast.LENGTH_LONG)
                                .show();
                        idScUsers.setRefreshing(false);
                        try{idLLUsers.removeView(idLLListUsers);}catch (Exception ex){}
                        try{idLLUsers.addView(idClRefresh);}catch (Exception ex){}
                        closePgDialog();
                    }
                });
    }

    public void onClickBtnRefresh(View view){
        showPgDialog(UsersActivity.this);
        this.getUsersFromApi();
    }

    @Override
    public void onClickListener(View view, int position) {

    }

    @Override
    public void onLongPressClickListener(View view, int position) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onRefresh() {
        this.getUsersFromApi();
    }
}
