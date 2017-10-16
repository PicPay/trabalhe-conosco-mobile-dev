package mobile.picpay.com.br.picpaymobile.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.Sort;
import mobile.picpay.com.br.picpaymobile.adapter.PessoasAdapter;
import mobile.picpay.com.br.picpaymobile.dao.PessoaDAO;
import mobile.picpay.com.br.picpaymobile.entity.Pessoa;
import mobile.picpay.com.br.picpaymobile.R;

public class PessoasActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    private RecyclerView recyclePessoas;
    private OrderedRealmCollection<Pessoa> pessoas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclePessoas = (RecyclerView) findViewById(R.id.recyclePessoas);
        recyclePessoas.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclePessoas.setLayoutManager(llm);

        PessoaDAO pessoaDAO = new PessoaDAO(this);
        pessoas = pessoaDAO.getAll().sort("name", Sort.ASCENDING);

        PessoasAdapter adapter = new PessoasAdapter(this, pessoas, this);
        recyclePessoas.setAdapter(adapter);





    }

}
