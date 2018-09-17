package com.picpay.david.davidrockpicpay

import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.support.v4.app.ActivityCompat
import com.picpay.david.davidrockpicpay.api.IPicPayAPI
import com.picpay.david.davidrockpicpay.api.PicPayAPI
import com.picpay.david.davidrockpicpay.entities.MyObjectBox
import com.readystatesoftware.chuck.ChuckInterceptor
import io.objectbox.BoxStore
//import io.realm.Realm
//import io.realm.RealmConfiguration
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import java.util.*
import java.util.concurrent.TimeUnit

class DavidRockPicPayApplication : Application() {

    companion object {
        lateinit var instance: DavidRockPicPayApplication
        lateinit var api: IPicPayAPI
        lateinit var httpClient: OkHttpClient
        lateinit var boxStore: BoxStore

    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        configClient()
        api = PicPayAPI(httpClient).getInstance()

        configFonts()
        configLocalDb()

    }

    private fun configClient() {
        val clientBuilder = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
                .connectTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(ChuckInterceptor(this))
        httpClient = clientBuilder.build()
    }

    private fun configFonts() {
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Product Sans Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        )
    }

    private fun configLocalDb() {
        // do this once, for example in your Application class
        boxStore = MyObjectBox.builder().androidContext(this).build()
//        Realm.init(this)
//        val realmConfig = RealmConfiguration.Builder()
//                .name("picpayrocks")
//                .schemaVersion(22)
//                .deleteRealmIfMigrationNeeded()
//                .build()
//        Realm.setDefaultConfiguration(realmConfig)
    }
}