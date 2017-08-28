package com.tmontovaneli.picpaychallenge.service;

import com.tmontovaneli.picpaychallenge.model.Payment;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by tmontovaneli on 23/08/17.
 */

public interface PaymentService {


    @POST("transaction")
    Call<ResponseBody> pay(@Body Payment payment);


}
