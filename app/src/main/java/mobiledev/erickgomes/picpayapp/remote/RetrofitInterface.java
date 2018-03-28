package mobiledev.erickgomes.picpayapp.remote;

import java.util.List;

import io.reactivex.Observable;
import mobiledev.erickgomes.picpayapp.models.Friend;
import mobiledev.erickgomes.picpayapp.models.TransactionResponse;
import mobiledev.erickgomes.picpayapp.models.TransactionSender;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by erickgomes on 23/03/2018.
 */

public interface RetrofitInterface {

    @POST("transaction")
    Observable<TransactionResponse> sendPayment (@Body TransactionSender sender);

    @GET("users")
    Observable<List<Friend>> getListFriends();

}
