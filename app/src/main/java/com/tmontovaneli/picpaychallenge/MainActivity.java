package com.tmontovaneli.picpaychallenge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tmontovaneli.picpaychallenge.adapter.ListUserAdapter;
import com.tmontovaneli.picpaychallenge.model.User;
import com.tmontovaneli.picpaychallenge.retrofit.UserRetrofitConfig;
import com.tmontovaneli.picpaychallenge.service.UserService;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "main";
    private ListView usuarioList;
    private View progress;
    private SearchView mSearchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Usuários");

        UserService userService = new UserRetrofitConfig().getUserService();
        Call<List<User>> listCall = userService.listRepos();

        usuarioList = (ListView) findViewById(R.id.listview);
        usuarioList.setTextFilterEnabled(true);


        progress = findViewById(R.id.progress);

        usuarioList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ListUserAdapter adapter = (ListUserAdapter) adapterView.getAdapter();
                User user = (User) adapter.getItem(i);

                Intent vaiParaPagamento = new Intent(MainActivity.this, PagamentoActivity.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable(PagamentoActivity.PARAM_USER, user);
                vaiParaPagamento.putExtras(bundle);

                startActivity(vaiParaPagamento);

            }
        });


        listCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> users = response.body();
                Collections.sort(users);
                usuarioList.setAdapter(new ListUserAdapter(MainActivity.this, users));

                usuarioList.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                t.printStackTrace();
                Log.e(TAG, "Erro ao buscar usuarios", t);
            }

        });


    }

    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                buscarUsuarios(newText);
                return true;
            }


            private void buscarUsuarios(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    usuarioList.clearTextFilter();
                } else {

                    if (newText.length() >= 3)
                        usuarioList.setFilterText(newText);
                }
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        //Pega o Componente.
        mSearchView = (SearchView) menu.findItem(R.id.search)
                .getActionView();

        //Define um texto de ajuda:
        mSearchView.setQueryHint("Nome do usuário");

        setupSearchView();

        return true;
    }


}
