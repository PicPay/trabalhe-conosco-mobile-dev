package com.tmontovaneli.picpaychallenge;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmontovaneli.picpaychallenge.dao.CartaoDAO;
import com.tmontovaneli.picpaychallenge.model.Cartao;
import com.tmontovaneli.picpaychallenge.model.Payment;
import com.tmontovaneli.picpaychallenge.model.Transaction;
import com.tmontovaneli.picpaychallenge.model.User;
import com.tmontovaneli.picpaychallenge.retrofit.UserRetrofitConfig;
import com.tmontovaneli.picpaychallenge.service.PaymentService;
import com.tmontovaneli.picpaychallenge.util.DataHelper;
import com.tmontovaneli.picpaychallenge.util.StringHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagamentoActivity extends AppCompatActivity {

    public static final String PARAM_USER = "user_pagamento";
    private EditText numeroCartao;
    private EditText cvv;
    private EditText dataExpiracao;
    private EditText valor;

    private User user;
    private Cartao cartao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);

        final AQuery aQuery = new AQuery(this);

        setTitle("Pagamento");

        Bundle bundle = getIntent().getExtras();
        user = (User) bundle.getSerializable(PARAM_USER);

        aQuery.id(R.id.textview_nome).text(user.getName());
        aQuery.id(R.id.textview_nickname).text(user.getUsername());
        aQuery.id(R.id.avatar).image(user.getImg(), true, true, 0, 0, null, AQuery.FADE_IN_NETWORK, 1.0f);

        this.numeroCartao = aQuery.id(R.id.edittext_numero_cartao).enabled(false).getEditText();
        this.cvv = aQuery.id(R.id.edittext_cvv).enabled(false).getEditText();
        this.dataExpiracao = aQuery.id(R.id.data_expiracao).enabled(false).getEditText();
        this.valor = aQuery.id(R.id.valor).getEditText();

        aQuery.id(R.id.layoutCartao)
                .clickable(true)
                .clicked(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CartaoDAO cartaoDAO = new CartaoDAO(PagamentoActivity.this);
                        final List<Cartao> cartaos = cartaoDAO.buscaCartoes();
                        if (cartaos == null || cartaos.isEmpty()) {
                            cadastrarCartao();
                            return;
                        }


                        CharSequence[] items = new CharSequence[cartaos.size()];
                        for (int i = 0; i < cartaos.size(); i++) {
                            items[i] = cartaos.get(i).getNumero();
                        }

                        AlertDialog.Builder builder = new AlertDialog.Builder(PagamentoActivity.this);
                        builder.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                cartao = cartaos.get(i);

                                numeroCartao.setText(cartao.getNumero());
                                cvv.setText(cartao.getCvv());
                                dataExpiracao.setText(cartao.getDataExpiracaoFormatada());


                            }
                        })
                                .setTitle("Selecione o cartão")
                                .create()
                                .show();

                    }
                });


        aQuery.id(R.id.btn_confirmar).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String temp = valor.getText().toString();
                if (StringHelper.isEmpty(temp)) {
                    valor.setError("Obrigatório");
                    return;
                }

                Payment payment = new Payment();
                payment.setCvv(Integer.valueOf(cartao.getCvv()));
                payment.setNrCartao(cartao.getNumero());
                payment.setDtExpiracao(DataHelper.formatMMBrYY(cartao.getExpiracao()));
                payment.setIdUser(user.getId().intValue());
                payment.setValue(Double.valueOf(valor.getText().toString()));


                PaymentService paymentService = new UserRetrofitConfig().getPaymentService();
                Call<ResponseBody> paymentCall = paymentService.pay(payment);
                paymentCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.i("tostring", response.toString());
                        try {

                            JSONObject jsonObject = new JSONObject(response.body().string());

                            String transaction = jsonObject.get("transaction").toString();

                            Log.i("json", transaction);

                            ObjectMapper objectMapper = new ObjectMapper();
                            Transaction result = objectMapper.readValue(transaction, Transaction.class);

                            Log.i("transa", result.toString());

                            if (result.isSuccess()) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(PagamentoActivity.this);
                                builder.setCancelable(false);
                                builder.setTitle("Informação");
                                builder.setMessage(String.format("Pagamento de %.2f realizado com sucesso.", result.getValue()));
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        PagamentoActivity.this.finish();
                                    }
                                });
                                builder.show();

                            } else {

                                final AlertDialog.Builder builder = new AlertDialog.Builder(PagamentoActivity.this);
                                builder.setCancelable(false);
                                builder.setTitle("Informação");
                                builder.setMessage(String.format("Pagamento de %.2f recusado.", result.getValue()));
                                builder.setNegativeButton("Tentar novamente", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        PagamentoActivity.this.finish();
                                    }
                                });
                                builder.show();

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("transaction", e.getMessage());
                            Toast.makeText(PagamentoActivity.this, "Erro ao realizar pagamento", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cartao, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_cartao:
                cadastrarCartao();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void cadastrarCartao() {
        Intent vaiParaCartao = new Intent(this, CartaoActivity.class);
        startActivity(vaiParaCartao);
    }
}
