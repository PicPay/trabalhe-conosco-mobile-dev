package com.example.filipe.enviadinheiro;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.filipe.adapter.UserAdapter;
import com.example.filipe.application.MyApplication;
import com.example.filipe.interfaces.RecyclerViewOnClickListenerHack;
import com.example.filipe.model.CartaoCredito;
import com.example.filipe.model.User;
import com.example.filipe.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewOnClickListenerHack {

    static final int PICK_TRANSACAO_REQUEST = 1;

    private List<User> mListUsers;

    private RecyclerView mRecyclerViewUser;
    private UserAdapter mUserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mListUsers = new ArrayList<>();

        mRecyclerViewUser = (RecyclerView) findViewById(R.id.rv_users);
        mRecyclerViewUser.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerViewUser.setLayoutManager(linearLayoutManager);

        mUserAdapter = new UserAdapter(this, mListUsers);
        mUserAdapter.setRecyclerViewOnClickListenerHack(this);

        mRecyclerViewUser.setAdapter(mUserAdapter);

        getUser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickListener(View view, int position) {
        Intent intent = new Intent(this, TransacaoActivity.class);
        intent.putExtra("usuario", mListUsers.get(position));
        //startActivity(intent);
        startActivityForResult(intent, PICK_TRANSACAO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_TRANSACAO_REQUEST){
            if(resultCode == RESULT_OK){
                Snackbar.make(MainActivity.this.findViewById(R.id.coordinator),
                        "Transação realizada com sucesso",
                        Snackbar.LENGTH_LONG)
                        .show();
            }
        }
    }

    private void getUser(){

        if(Util.verificaConexaoInternet(this)){

            String tag = "json_array_request";
            String url = "http://careers.picpay.com/tests/mobdev/users";

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            JSONArray jsonArrayUser = response;

                            for(int i = 0; i < jsonArrayUser.length(); i++){
                                try {
                                    JSONObject jsonObjectUser = jsonArrayUser.getJSONObject(i);

                                    User user = new User();
                                    user.setId(jsonObjectUser.getInt("id"));
                                    user.setName(jsonObjectUser.getString("name"));
                                    user.setUsername(jsonObjectUser.getString("username"));
                                    user.setImg(jsonObjectUser.getString("img"));

                                    mListUsers.add(user);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            mUserAdapter.setDados(mListUsers);

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("LOG", "Requisição Falhou");
                        }
                    });

            MyApplication.getInstance().addToRequestQueue(jsonArrayRequest, tag);
        }else{
            Snackbar.make(MainActivity.this.findViewById(R.id.coordinator),
                    "É necessario conexão com a internet",
                    Snackbar.LENGTH_SHORT)
                    .show();
        }
    }
}
