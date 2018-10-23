package stolato.com.br.paypalpayment.API;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import stolato.com.br.paypalpayment.Model.Cliente;

/**
 * Created by luiz on 19/06/17.
 */

public interface Data {
    @GET("users")
    Call<List<Cliente>> getUsers();

    @POST("transaction")
    Call<ResponseBody> Send(@Body RequestBody params);
}
