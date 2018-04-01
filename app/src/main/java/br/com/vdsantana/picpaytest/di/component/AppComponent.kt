package br.com.vdsantana.picpaytest.di.component

import android.app.Application
import android.content.res.Resources
import br.com.vdsantana.picpaytest.api.ApiInterface
import br.com.vdsantana.picpaytest.di.modules.ApiModule
import br.com.vdsantana.picpaytest.di.modules.AppModule
import br.com.vdsantana.picpaytest.di.modules.OkHttpModule
import br.com.vdsantana.picpaytest.di.modules.RetrofitModule
import br.com.vdsantana.picpaytest.utils.AppSchedulerProvider
import com.google.gson.Gson
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by vd_sa on 30/03/2018.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class, RetrofitModule::class, ApiModule::class, OkHttpModule::class))
interface AppComponent {
    fun application(): Application
    fun gson(): Gson
    fun resources(): Resources
    fun retrofit(): Retrofit
    fun apiInterface(): ApiInterface
    fun cache(): Cache
    fun client(): OkHttpClient
    fun loggingInterceptor(): HttpLoggingInterceptor
    fun compositeDisposable(): CompositeDisposable
    fun schedulerProvider(): AppSchedulerProvider
}