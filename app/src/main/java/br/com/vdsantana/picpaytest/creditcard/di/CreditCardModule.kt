package br.com.vdsantana.picpaytest.creditcard.di

import android.app.Application
import br.com.vdsantana.picpaytest.di.ActivityScope
import br.com.vdsantana.picpaytest.utils.security.SecStore
import com.google.gson.Gson
import dagger.Module
import dagger.Provides

/**
 * Created by vd_sa on 30/03/2018.
 */
@Module
class CreditCardModule {

    @Provides
    @ActivityScope
    internal fun providesSecStore(gson: Gson, application: Application): SecStore = SecStore(gson, application)
}