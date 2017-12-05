package viniciusmaia.com.vinipay.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import viniciusmaia.com.vinipay.R;
import viniciusmaia.com.vinipay.modelo.CartaoCredito;
import viniciusmaia.com.vinipay.modelo.ResponseTransacao;
import viniciusmaia.com.vinipay.modelo.Transacao;
import viniciusmaia.com.vinipay.restclient.PicPayRestClient;
import viniciusmaia.com.vinipay.util.ControleSessao;

/**
 * Created by User on 04/12/2017.
 */

public class TransacaoActivity extends AppCompatActivity {

    private int idUsuario;
    private String usuario;
    private Realm realm;
    private Retrofit retrofit;
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
                double valorTransacao = getValorTransacao();

                if (isTransacaoValida(valorTransacao)){
                    processarPagamento(valorTransacao);
                }
            }
        });
    }

    private void inflateToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.titulo_transacao);

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

    private void processarPagamento(double valor){
        ControleSessao controleSessao = new ControleSessao(this);
        RealmResults<CartaoCredito> cartoes = realm.where(CartaoCredito.class).equalTo("idUsuario", controleSessao.getIdUsuario()).findAll();

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
            transacao.setValor(valor);

            if (retrofit == null){
                inicializaRetrofit();
            }

            try{
                PicPayRestClient restClient = retrofit.create(PicPayRestClient.class);
                Call<ResponseTransacao> call = restClient.realizaTransacao(transacao);
                call.enqueue(new Callback<ResponseTransacao>() {
                    @Override
                    public void onResponse(Call<ResponseTransacao> call, Response<ResponseTransacao> response) {
                        ResponseTransacao responseTransacao = response.body();

                        if (responseTransacao.getResultado().getStatus().equals("Aprovada")){
                            Toast.makeText(TransacaoActivity.this, "Pagamento realizado com sucesso!", Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else{
                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(TransacaoActivity.this);
                            dialogBuilder.setTitle("Erro");
                            dialogBuilder.setMessage("Não foi possível realizar o pagamento. Por favor, verifique os dados do seu cartão e tente novamente!");
                            dialogBuilder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            dialogBuilder.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseTransacao> call, Throwable t) {
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(TransacaoActivity.this);
                        dialogBuilder.setTitle("Erro");
                        dialogBuilder.setMessage("Não foi possível realizar o pagamento. Por favor, verifique os dados do seu cartão e tente novamente!");
                        dialogBuilder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialogBuilder.show();
                    }
                });
            }
            catch (Exception e){
                e.printStackTrace();
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(TransacaoActivity.this);
                dialogBuilder.setTitle("Erro");
                dialogBuilder.setMessage(e.getMessage());
                dialogBuilder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            }
        }
    }

    private boolean isTransacaoValida(double valor){

        if (valor <= 0.01){
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle("Atenção");
            dialogBuilder.setMessage("O valor do pagamento deve ser maior que 0.00");
            dialogBuilder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialogBuilder.show();

            return false;
        }
        return true;
    }

    private double getValorTransacao(){
        EditText editValor = (EditText) findViewById(R.id.editValor);
        String valor = editValor.getText().toString();
        valor = valor.replace(".", "");
        valor = valor.replace(",", ".");
        valor = valor.replace("R$", "");

        return Double.parseDouble(valor);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ADICIONAR_CARTAO && resultCode == RESULT_OK){

            double valorTransacao = getValorTransacao();

            if(isTransacaoValida(valorTransacao)){
                processarPagamento(valorTransacao);
            }
        }
    }

    private void inicializaRetrofit(){
        retrofit = new Retrofit.Builder().
                baseUrl(getText(R.string.url_rest).toString()).
                addConverterFactory(GsonConverterFactory.create()).
                build();
    }
}
