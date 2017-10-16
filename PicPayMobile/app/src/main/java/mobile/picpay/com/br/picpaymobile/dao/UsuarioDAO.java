package mobile.picpay.com.br.picpaymobile.dao;

import android.app.Activity;

import io.realm.Realm;
import io.realm.RealmQuery;
import mobile.picpay.com.br.picpaymobile.entity.Usuario;


public class UsuarioDAO extends GenericDao<Usuario> {

    public UsuarioDAO(Activity context) {
        super(context, Usuario.class);
    }


    public Usuario getUsusario(String username, String senha) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Usuario> query = realm.where(Usuario.class);
        query.equalTo("username", username);
        Usuario result = query.findFirst();

        if (result != null && result.getSenha().equals(senha)) {
            return result;
        }

        return null;
    }



}