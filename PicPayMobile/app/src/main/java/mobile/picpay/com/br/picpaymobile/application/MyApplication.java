package mobile.picpay.com.br.picpaymobile.application;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import mobile.picpay.com.br.picpaymobile.entity.Usuario;
import mobile.picpay.com.br.picpaymobile.migration.MyMigration;


public class MyApplication extends Application {

    private static MyApplication mApplication;

    private Usuario usuario;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration configRealm = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .migration(new MyMigration())
                .build();
        Realm.setDefaultConfiguration(configRealm);

        mApplication = this;

    }

    public static MyApplication getInstance(){
        return mApplication;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
