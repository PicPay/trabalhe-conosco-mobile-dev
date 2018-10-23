package stolato.com.br.paypalpayment;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import stolato.com.br.paypalpayment.API.Data;
import stolato.com.br.paypalpayment.Adapter.ClienteAdapter;
import stolato.com.br.paypalpayment.DB.Banco;
import stolato.com.br.paypalpayment.Model.Cliente;
import stolato.com.br.paypalpayment.Model.ClientesDes;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private ArrayList<Cliente> clientes;
    private ArrayAdapter adapter;
    public static final String URL = "http://careers.picpay.com/tests/mobdev/";
    private ConstraintLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main = (ConstraintLayout) findViewById(R.id.LayoutMain);

        ImageView logo_wgas = new ImageView(getApplicationContext());
        logo_wgas.setImageResource(R.drawable.logowhite);
        getSupportActionBar().setCustomView(logo_wgas,new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        clientes = new ArrayList<Cliente>();
        adapter = new ClienteAdapter(getApplication(),R.layout.lista_clientes,clientes);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                chamada();
            }
        });
        listView = (ListView) findViewById(R.id.ListUser);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cliente cliente = clientes.get(i);
                Intent intent = new Intent(getApplicationContext(),Transition.class);
                intent.putExtra("cliente",cliente);
                startActivity(intent);
            }
        });
        listView.setAdapter(adapter);
        chamada();

        Banco bd = new Banco();
        bd.startBanco(getApplicationContext());
        if(!bd.checkCards()) {
            final Snackbar snackbar = Snackbar.make(main, "Você ainda não tem nenhum cartão cadastrado", Snackbar.LENGTH_INDEFINITE);
            snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
            snackbar.setAction("Cadastrar", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplication(), AddCartActivity.class);
                    startActivity(intent);
                    snackbar.dismiss();
                }
            });
            snackbar.show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int it = item.getItemId();
        switch (item.getItemId()) {
            case R.id.about:
                Intent intent = new Intent(getApplicationContext(),AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.menucarts:
                Intent carts = new Intent(MainActivity.this,CartsActivity.class);
                startActivity(carts);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void chamada(){
        swipeRefreshLayout.setRefreshing(true);
        Gson gson = new GsonBuilder().registerTypeAdapter(Cliente.class, new ClientesDes()).create();
        Retrofit retrofit = new Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(URL)
                .build();
        Data data = retrofit.create(Data.class);
        Call<List<Cliente>> call = data.getUsers();
        call.enqueue(new Callback<List<Cliente>>() {
            @Override
            public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {
                List<Cliente> data = response.body();
                if (data != null) {
                    for (Cliente t : data) {
                        //ids.add(t.getId());
                        //nomes.add(t.getName());
                        clientes.add(new Cliente(t.getId(), t.getImg(), t.getName(),t.getUsername()));
                        adapter.notifyDataSetChanged();
                    }
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<Cliente>> call, Throwable t) {
                Log.i("Resposta",t.getMessage());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        swipeRefreshLayout.setRefreshing(false);
    }
}
