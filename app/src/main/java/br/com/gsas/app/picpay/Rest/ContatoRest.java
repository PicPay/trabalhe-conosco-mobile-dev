package br.com.gsas.app.picpay.Rest;

import java.util.List;

import br.com.gsas.app.picpay.Domain.Contato;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ContatoRest{

        @GET("tests/mobdev/users")
        Call<List<Contato>> listaContato();
}
