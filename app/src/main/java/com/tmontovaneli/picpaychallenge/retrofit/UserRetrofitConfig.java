package com.tmontovaneli.picpaychallenge.retrofit;

import com.tmontovaneli.picpaychallenge.service.PaymentService;
import com.tmontovaneli.picpaychallenge.service.UserService;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by tmontovaneli on 21/08/17.
 */

public class UserRetrofitConfig {

    private final Retrofit retrofit;

    public UserRetrofitConfig() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(interceptor);


        retrofit = new Retrofit.Builder()
                .baseUrl("http://careers.picpay.com/tests/mobdev/")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public UserService getUserService() {
        return retrofit.create(UserService.class);
    }

    public PaymentService getPaymentService() {
        return retrofit.create(PaymentService.class);
    }

}
