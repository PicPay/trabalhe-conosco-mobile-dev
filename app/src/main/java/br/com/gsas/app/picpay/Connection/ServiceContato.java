package br.com.gsas.app.picpay.Connection;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import br.com.gsas.app.picpay.Domain.Contato;
import br.com.gsas.app.picpay.Rest.ContatoRest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceContato {

    private final String URL = "http://careers.picpay.com/";

    private ContatoRest rest;
    private Context context;


    public ServiceContato(Context context){

        this.context = context;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        rest = retrofit.create(ContatoRest.class);
    }

    public void getContatoWeb(final MyCallback<Contato> callback){

        Log.d("Contato", "Pegando da WEB");

        Call<List<Contato>> call = rest.listaContato();

        call.enqueue(new Callback<List<Contato>>() {
            @Override
            public void onResponse(@NonNull Call<List<Contato>> call, @NonNull Response<List<Contato>> response) {

                if(response.isSuccessful() && response.body() != null){

                    if(response.body().isEmpty()){
                        Log.d("Contato", "Vazio WEB");
                        callback.empty();
                    }

                    else{
                        Log.d("Contato", "Sucesso WEB");
                        callback.sucess(response.body());
                    }
                }

                else {
                    Log.d("Contato", "Erro WEB");
                    callback.failure("Failure Contato Web");
                }
            }
            @Override
            public void onFailure(Call<List<Contato>> call, Throwable t) {

                Log.d("Contato", "Erro WEB");

                t.printStackTrace();
                callback.failure("Failure Contato Web");

            }
        });

    }

}
