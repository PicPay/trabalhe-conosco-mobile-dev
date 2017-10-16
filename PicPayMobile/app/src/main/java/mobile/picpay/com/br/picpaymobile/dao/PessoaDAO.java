package mobile.picpay.com.br.picpaymobile.dao;

import android.app.Activity;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmQuery;
import mobile.picpay.com.br.picpaymobile.entity.Pessoa;


public class PessoaDAO extends GenericDao<Pessoa> {

    public PessoaDAO(Activity context) {
        super(context, Pessoa.class);
    }



}