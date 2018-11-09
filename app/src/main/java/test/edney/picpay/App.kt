package test.edney.picpay

import android.app.Application
import com.squareup.leakcanary.LeakCanary

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initLeakcanary()
    }

    private fun initLeakcanary(){
        if (BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this))
                return
            LeakCanary.install(this)
        }
    }
}