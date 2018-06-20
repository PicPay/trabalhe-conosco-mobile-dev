package br.com.gsas.app.picpay.Connection;

import android.content.Context;
import android.util.Log;

import br.com.gsas.app.picpay.Domain.Pagamento;
import br.com.gsas.app.picpay.Domain.Transaction;
import br.com.gsas.app.picpay.Rest.PagamentoRest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicePagamento {

    private final String URL = "http://careers.picpay.com/";

    private PagamentoRest rest;
    private Context context;


    public ServicePagamento(Context context){

        this.context = context;


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        rest = retrofit.create(PagamentoRest.class);
    }

    public void postPagamento(final OnCallback<Transaction> callback, Pagamento pagamento){

        Log.d("FEED", "POST");

        Call<Transaction> call = rest.postPagamento(pagamento);

        call.enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(Call<Transaction> call, Response<Transaction> response) {

                if(response.isSuccessful()){

                    callback.sucess(response.body());
                }

                else{

                    callback.empty();
                }
            }

            @Override
            public void onFailure(Call<Transaction> call, Throwable t) {

                callback.failure("Erro Post");
            }
        });

    }
}
