package project.picpay.test.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rodrigo Oliveira on 17/11/2018 for PicPay Test.
 * Contact us rodrigooliveira.tecinfo@gmail.com
 */
public class ApiClient {

    private static final ApiClient service = new ApiClient();
    private static ApiInterface api;

    private ApiClient() {
        int timeout = 300000;
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("Content-Type", "application/x-www-form-urlencoded")
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                })
                .build();

        api = new Retrofit.Builder()
                .baseUrl("http://careers.picpay.com/tests/mobdev/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(ApiInterface.class);
    }

    public static ApiInterface get() {
        return api;
    }

}