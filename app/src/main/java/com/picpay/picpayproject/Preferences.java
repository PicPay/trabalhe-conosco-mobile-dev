package com.picpay.picpayproject;

import android.content.Context;
import android.content.SharedPreferences;

import com.picpay.picpayproject.model.Cartao;
import com.picpay.picpayproject.model.Usuario;

/**
 * Created by felip on 21/07/2017.
 */

public class Preferences {

    private Context contexto;
    private SharedPreferences preferences;
    private final String NOME_ARQUIVO = "picpay.preferencias";
    private final int MODE = 0;
    private SharedPreferences.Editor editor;
    private String id;

    private final String CHAVE_IDENTIFICADOR = "ID_USUARIO_LOGADO";

    public Preferences(Context context){

        contexto = context;
        preferences = contexto.getSharedPreferences(NOME_ARQUIVO,MODE);
        editor = preferences.edit();

    }
        public void salvarId(String id){
        editor.putString(CHAVE_IDENTIFICADOR,id);
        editor.commit();
    }

    public String recuperaid(){
        id = preferences.getString(CHAVE_IDENTIFICADOR,null);
        return id;
    }
}
