package com.ghrc.picpay.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ghrc.picpay.R;
import com.ghrc.picpay.api.PicPayApi;
import com.ghrc.picpay.model.CreditCard;
import com.ghrc.picpay.model.Transaction;
import com.ghrc.picpay.model.User;
import com.ghrc.picpay.util.BD;
import com.ghrc.picpay.util.CircleTransform;
import com.ghrc.picpay.util.Config;
import com.ghrc.picpay.util.CurrencyTextWatcher;
import com.ghrc.picpay.util.DateUtil;
import com.ghrc.picpay.util.MyApplication;
import com.ghrc.picpay.util.NumberUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PayActivity extends AppCompatActivity {

    private  MaterialBetterSpinner materialBetterSpinner ;
    private ArrayList<CreditCard> mListCard;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        final User user  = getIntent().getParcelableExtra("user");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Pagamento");
        ImageView photoUser = (ImageView) findViewById(R.id.imageView);
        Picasso.with(this).load(user.getImg()).transform(new CircleTransform()).into(photoUser);
        TextView txtName = (TextView) findViewById(R.id.textView);
        txtName.setText(user.getName()+ "\n" + user.getUsername() );
        BD bd = new BD(this);
        mListCard = bd.getCards();
        List<String> list = new ArrayList<>();
        for (int i = 0; i <mListCard.size() ; i++) {
            list.add( "XXXX XXXX XXXX "+mListCard.get(i).getCard_number().substring(mListCard.get(i).getCard_number().length() -4));
        }
        materialBetterSpinner = (MaterialBetterSpinner)findViewById(R.id.material_spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(PayActivity.this, android.R.layout.simple_dropdown_item_1line, list);
        materialBetterSpinner.setAdapter(adapter);
        materialBetterSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
            }
        });
        final EditText edtMoney =(EditText) findViewById(R.id.edt_money);
        edtMoney.addTextChangedListener(new CurrencyTextWatcher(edtMoney));
        final TextInputLayout inputLayoutMoney = (TextInputLayout) findViewById(R.id.edt_money_input);
        Button btnSendPag = (Button) findViewById(R.id.btnSendPag);
        btnSendPag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(materialBetterSpinner.getText().toString().length() == 0){
                    materialBetterSpinner.setError("Selecione o cartão");
                    return;
                }
                if(edtMoney.getText().toString().length() == 0){
                    inputLayoutMoney.setError("Informe o valor");
                    return;
                }
                final ProgressDialog progressDialog = new ProgressDialog(PayActivity.this);
                progressDialog.setMessage("Aguarde... Enviando o pagamento.");
                progressDialog.setCancelable(false);
                AlertDialog.Builder builder = new AlertDialog.Builder(PayActivity.this);
                builder.setTitle("Confirma Pagamento")
                        .setMessage("Você realmente deseja enviar o pagamento para o " + user.getUsername() + " ?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                progressDialog.show();
                                final Transaction transaction = new Transaction();
                                transaction.setCard_number(mListCard.get(pos).getCard_number());
                                transaction.setCvv(mListCard.get(pos).getCvv());
                                transaction.setExpiry_date(mListCard.get(pos).getExpiry_date());
                                transaction.setDestination_user_id(user.getId());
                                transaction.setValue(NumberUtil.ParseNumber(edtMoney.getText().toString()));
                                Retrofit retrofit = new Retrofit
                                        .Builder()
                                        .baseUrl(Config.API)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                PicPayApi api = retrofit.create(PicPayApi.class);
                                Gson gson = new GsonBuilder().create();
                                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(transaction));
                                Call<JsonObject> transCall = api.sendTransaction(body);
                                transCall.enqueue(new Callback<JsonObject>() {
                                    @Override
                                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                        progressDialog.dismiss();
                                        transaction.setData(DateUtil.getDateTime());
                                        ((MyApplication)getApplicationContext()).getInstanceBD().inserTransaction(transaction);
                                        Toast.makeText(getApplicationContext(), "Pagamento feito com sucesso.", Toast.LENGTH_SHORT).show();
                                        PayActivity.this.finish();
                                    }

                                    @Override
                                    public void onFailure(Call<JsonObject> call, Throwable t) {

                                    }
                                });
                            }
                        }).setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });
    }

}
