package project.picpay.test.api;

import java.util.List;

import okhttp3.RequestBody;
import project.picpay.test.home.model.transaction_sended.ResponseTransaction;
import project.picpay.test.home.model.UserModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Rodrigo Oliveira on 17/11/2018 for PicPay Test.
 * Contact us rodrigooliveira.tecinfo@gmail.com
 */
public interface ApiInterface {

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @GET("users")
    Call<List<UserModel>> getAvailableUsers();

    @Headers({"Content-Type: application/json"})
    @POST("transaction")
    Call<ResponseTransaction> postTransaction(@Body RequestBody body);

}