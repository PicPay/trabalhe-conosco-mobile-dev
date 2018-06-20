package br.com.gsas.app.picpay.Pagamento;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;

import br.com.gsas.app.picpay.Carteira.CarteiraActivity;
import br.com.gsas.app.picpay.Connection.OnCallback;
import br.com.gsas.app.picpay.Connection.ServiceCartao;
import br.com.gsas.app.picpay.Connection.ServiceFeed;
import br.com.gsas.app.picpay.Connection.ServicePagamento;
import br.com.gsas.app.picpay.Domain.Cartao;
import br.com.gsas.app.picpay.Domain.Contato;
import br.com.gsas.app.picpay.Domain.Feed;
import br.com.gsas.app.picpay.Domain.Pagamento;
import br.com.gsas.app.picpay.Domain.Transaction;
import br.com.gsas.app.picpay.Home.MainActivity;
import br.com.gsas.app.picpay.R;
import br.com.gsas.app.picpay.Util.DialogAceitar;
import faranjit.currency.edittext.CurrencyEditText;

public class PagamentoActivity extends AppCompatActivity {

    private ProgressDialog dialog;

    private Toolbar toolbar;
    private ImageView imagem;
    private TextView username;
    private TextView final_cartao;
    private CurrencyEditText valor;
    private RelativeLayout mudar_cartao;
    private RelativeLayout sem_carto;
    private Button pagar;
    private Button pro;
    protected EditText msg_pagamento;

    private Cartao cartao;
    private Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);

        contato = (Contato) getIntent().getSerializableExtra("USER");

        setUpLayput();
        setToolbar();
        insertLayout();

        mudar_cartao.setOnClickListener(listenerMuda());
        sem_carto.setOnClickListener(listenerMuda());
        pagar.setOnClickListener(fazerPagamento());
        pro.setOnClickListener(pagamentoPro());
    }

    private View.OnClickListener pagamentoPro() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogAceitar dialogAceitar = DialogAceitar.newInstance(getString(R.string.text_pro));
                dialogAceitar.show(getSupportFragmentManager(), "Pic-Pro");
            }
        };
    }

    private View.OnClickListener fazerPagamento() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cartao != null){

                    if(valor.getText().toString().length() != 0){

                        String data;

                        Pagamento pagamento = new Pagamento();
                        pagamento.setCard_number(cartao.getCard_nunber());
                        pagamento.setCvv(Integer.parseInt(cartao.getCvv()));


                        try {
                            Double dinheiro = valor.getCurrencyDouble();
                            pagamento.setValue(dinheiro);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        data = cartao.getMes() + "/" + cartao.getAno();

                        pagamento.setExpiry_date(data);

                        pagamento.setDestination_user_id(contato.getId());

                        startLoad();

                        ServicePagamento service = new ServicePagamento(PagamentoActivity.this);
                        service.postPagamento(callbackPost(), pagamento);
                    }

                    else{

                        DialogAceitar aceitar = DialogAceitar.newInstance(getString(R.string.sem_valor));
                        aceitar.show(getSupportFragmentManager(), "Sem_Valor");
                    }
                }
                else{

                    DialogAceitar aceitar = DialogAceitar.newInstance(getString(R.string.sem_cartao));
                    aceitar.show(getSupportFragmentManager(), "Sem_Cartão");

                }
            }
        };

    }


    private OnCallback<Transaction> callbackPost() {

        return new OnCallback<Transaction>() {
            @Override
            public void sucess(Transaction element) {

                if(element.getTransaction().getStatus().equals("Aprovada") && element.getTransaction().isSuccess()){

                    ServiceFeed serviceFeed = new ServiceFeed(PagamentoActivity.this);
                    element.getTransaction().setMsg(msg_pagamento.getText().toString());
                    serviceFeed.insertBD(inserirFeed(), element.getTransaction());
                }

                else{

                    cancelLoad();

                    DialogAceitar aceitar = DialogAceitar.newInstance(getString(R.string.erro_cartao));
                    aceitar.show(getSupportFragmentManager(), "Erro_Cartão");
                }

            }

            @Override
            public void empty() {

                cancelLoad();

                DialogAceitar aceitar = DialogAceitar.newInstance(getString(R.string.erro_pagar));
                aceitar.show(getSupportFragmentManager(), "Erro");

            }

            @Override
            public void failure(String msg) {

                cancelLoad();

                DialogAceitar aceitar = DialogAceitar.newInstance(getString(R.string.internet_pagar));
                aceitar.show(getSupportFragmentManager(), "Internet");

            }
        };
    }

    private OnCallback<Feed> inserirFeed() {

        return new OnCallback<Feed>() {
            @Override
            public void sucess(Feed element) {

                Log.d("Salvou", "Pagamento");
                cancelLoad();

                Intent intent = new Intent(PagamentoActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

            @Override
            public void empty() {

            }

            @Override
            public void failure(String msg) {

            }
        };
    }

    private View.OnClickListener listenerMuda() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PagamentoActivity.this, CarteiraActivity.class);
                startActivity(intent);
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();

        ServiceCartao serviceCartao = new ServiceCartao(PagamentoActivity.this);
        serviceCartao.getAtivoBD(callbackAtivo());

    }

    private OnCallback<Cartao> callbackAtivo() {

        return new OnCallback<Cartao>() {
            @Override
            public void sucess(Cartao element) {

                mudar_cartao.setVisibility(View.VISIBLE);
                sem_carto.setVisibility(View.GONE);

                PagamentoActivity.this.cartao = element;
                String numero = element.getCard_nunber();
                final_cartao.setText(numero.substring(numero.length() - 4, numero.length()));
            }

            @Override
            public void empty() {

                mudar_cartao.setVisibility(View.GONE);
                sem_carto.setVisibility(View.VISIBLE);

            }

            @Override
            public void failure(String msg) {

                mudar_cartao.setVisibility(View.GONE);
                sem_carto.setVisibility(View.VISIBLE);

            }
        };
    }

    private void insertLayout() {

        username.setText(contato.getUsername());

        Picasso.with(PagamentoActivity.this)
                .load(contato.getImagem())
                .placeholder(R.drawable.fundo_teste)
                .error(R.drawable.fundo_teste)
                .into(imagem);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home){

            finish();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void setToolbar() {

        if (toolbar != null) {

            setSupportActionBar(toolbar);

            if(getSupportActionBar()!= null){

                getSupportActionBar().setTitle(getString(R.string.toolbar_pagamento));
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

        }
    }

    private void setUpLayput() {

        toolbar = findViewById(R.id.toolbar_home);
        imagem = findViewById(R.id.imagem_contato);
        username = findViewById(R.id.text_username);
        final_cartao = findViewById(R.id.final_cartao);
        mudar_cartao = findViewById(R.id.pagamento_muda);
        pagar = findViewById(R.id.pagar_pessoa);
        valor = findViewById(R.id.valor);
        msg_pagamento = findViewById(R.id.msg_pagamento);
        pro = findViewById(R.id.bot_pro);
        sem_carto = findViewById(R.id.pagamento_muda1);

    }

    private void startLoad (){

        dialog = new ProgressDialog(PagamentoActivity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(getString(R.string.text_aguarde));
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void cancelLoad(){

        dialog.cancel();
    }
}
