package gilianmarques.dev.picpay_test.utils;

import android.app.Application;
import android.content.Context;

import gilianmarques.dev.picpay_test.activities.MyActivity;

public class MyApp extends Application {

    /* evita que eu fique passando instancias de contexto apara o DataBase e outras classes*/
    private static Context mContext;
    /*uso essa instancia para mostrar uma dialogo sempre que a conxão do dispositivo cair*/
    private MyActivity currentActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }


    public  static  Context getContext() {
        return mContext;
    }

    public void setCurrentActivity(MyActivity currentActivity) {
        this.currentActivity = currentActivity;
    }

    public MyActivity getCurrentActivity() {
        return currentActivity;
    }
}
