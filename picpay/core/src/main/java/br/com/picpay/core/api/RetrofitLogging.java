package br.com.picpay.core.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

abstract class RetrofitLogging {
    protected static OkHttpClient.Builder logging(OkHttpClient.Builder clientBuilder) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(logging);
        return clientBuilder.addInterceptor(logging);
    }
}
