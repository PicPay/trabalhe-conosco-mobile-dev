package viniciusmaia.com.vinipay.restclient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import viniciusmaia.com.vinipay.modelo.ResponseTransacao;
import viniciusmaia.com.vinipay.modelo.Transacao;
import viniciusmaia.com.vinipay.modelo.Usuario;

/**
 * Created by User on 03/12/2017.
 */

public interface PicPayRestClient {
    @GET("users")
    Call<List<Usuario>> getUsuarios();

    @POST("transaction")
    Call<ResponseTransacao> realizaTransacao(@Body Transacao transacao);
}
