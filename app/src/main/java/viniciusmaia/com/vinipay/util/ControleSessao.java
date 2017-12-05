package viniciusmaia.com.vinipay.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import io.realm.PermissionRealmProxyInterface;
import viniciusmaia.com.vinipay.application.MyApplication;

/**
 * Created by User on 05/12/2017.
 */

public class ControleSessao {

    private SharedPreferences preferences;
    private static String USUARIO_KEY = "UsuarioKey";
    private static String ID_USUARIO = "IdKey";
    private static String USUARIO_LOGADO_KEY = "IsUsuarioLogadoKey";

    public ControleSessao(Context context){
        preferences = context.getSharedPreferences("ViniPaySession", Context.MODE_PRIVATE);
    }

    public void iniciaSessao(int idUsuario, String usuario){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(ID_USUARIO, idUsuario);
        editor.putString(USUARIO_KEY, usuario);
        editor.putBoolean(USUARIO_LOGADO_KEY, true);
        editor.commit();
    }

    public void encerraSessao(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    public boolean isUsuarioLogado(){
        return preferences.getBoolean(USUARIO_LOGADO_KEY, false);
    }

    public String getUsuario(){
        return preferences.getString(USUARIO_KEY, null);
    }

    public int getIdUsuario(){
        return preferences.getInt(ID_USUARIO, -1);
    }

}
