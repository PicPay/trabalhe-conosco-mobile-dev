package br.com.vdsantana.picpaytest.di.modules

import br.com.vdsantana.picpaytest.api.ApiInterface
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by vd_sa on 30/03/2018.
 */
@Module
class ApiModule {
    @Provides
    @Singleton
    fun providesEndpoints(retrofit: Retrofit): ApiInterface = retrofit.create(ApiInterface::class.java)
}