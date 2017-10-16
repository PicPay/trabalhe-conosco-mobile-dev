package mobile.picpay.com.br.picpaymobile.infra;

import org.json.JSONObject;

import java.util.List;

import mobile.picpay.com.br.picpaymobile.entity.Pessoa;
import mobile.picpay.com.br.picpaymobile.entity.RetTransacao;
import mobile.picpay.com.br.picpaymobile.entity.RootTransacao;
import mobile.picpay.com.br.picpaymobile.entity.StatusTransacao;
import mobile.picpay.com.br.picpaymobile.entity.Transacao;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by johonatan on 04/10/2017.
 */

public interface IacessoWs {


    @GET("users")
    Call<List<Pessoa>> getPessoas();


    @POST("transaction")
    Call<RootTransacao> putTransacao(@Body Transacao transacao);



}
