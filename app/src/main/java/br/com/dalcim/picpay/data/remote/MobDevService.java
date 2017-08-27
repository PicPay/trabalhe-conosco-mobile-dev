package br.com.dalcim.picpay.data.remote;

import java.util.List;

import br.com.dalcim.picpay.data.Payment;
import br.com.dalcim.picpay.data.TransactionResponse;
import br.com.dalcim.picpay.data.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author Wiliam
 * @since 25/08/2017
 */
public interface MobDevService {
    @GET("users")
    Call<List<User>> getUsers();

    @Headers({"Content-type: application/json"})
    @POST("transaction ")
    Call<TransactionResponse> transaction(@Body Payment payment);
}
