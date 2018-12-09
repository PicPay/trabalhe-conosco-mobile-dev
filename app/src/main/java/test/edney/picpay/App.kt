package test.edney.picpay

import android.app.Application
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initLeakcanary()
        Stetho.initializeWithDefaults(this)
    }

    private fun initLeakcanary(){
        if (BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this))
                return
            LeakCanary.install(this)
        }
    }
}