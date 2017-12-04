package viniciusmaia.com.vinipay.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import io.realm.Realm;
import io.realm.RealmResults;
import viniciusmaia.com.vinipay.R;
import viniciusmaia.com.vinipay.modelo.CartaoCredito;
import viniciusmaia.com.vinipay.modelo.Transacao;

/**
 * Created by User on 04/12/2017.
 */

public class TransacaoActivity extends AppCompatActivity {

    private int idUsuario;
    private String usuario;
    private Realm realm;
    private static final int REQUEST_ADICIONAR_CARTAO = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transacao);
        inflateToolbar();

        realm = Realm.getDefaultInstance();

        idUsuario = getIntent().getIntExtra("idUsuario", -1);
        usuario = getIntent().getStringExtra("usuario");

        TextView textBeneficiado = (TextView) findViewById(R.id.textBeneficiado);
        textBeneficiado.setText("Beneficiado: " + usuario);

        Button botaoPagar = (Button) findViewById(R.id.botaoPagar);
        botaoPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processarPagamento();
            }
        });
    }

    private void inflateToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.text_cartoes);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void processarPagamento(){
        RealmResults<CartaoCredito> cartoes = realm.where(CartaoCredito.class).equalTo("idUsuario", 1).findAll();

        if (cartoes == null || cartoes.size() == 0){
            Intent adicionarCartaoIntent = new Intent(this, MeuCartaoActivity.class);
            startActivityForResult(adicionarCartaoIntent, REQUEST_ADICIONAR_CARTAO);
        }
        else{
            CartaoCredito cartao = cartoes.get(0);
            Transacao transacao = new Transacao();

            transacao.setCodigoSeguranca(cartao.getCodigoSeguranca());
            transacao.setValidade(cartao.getValidade());
            transacao.setNumero(cartao.getNumero());
            transacao.setIdUsuario(idUsuario);

            
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ADICIONAR_CARTAO && resultCode == RESULT_OK){
            processarPagamento();
        }
    }
}
