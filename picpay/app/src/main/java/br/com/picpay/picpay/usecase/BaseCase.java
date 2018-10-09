package br.com.picpay.picpay.usecase;

import org.androidannotations.annotations.EBean;

import javax.annotation.Nonnull;

import io.realm.Realm;

@EBean
public class BaseCase {

    private Realm realm = Realm.getDefaultInstance();

    @Nonnull
    public Realm getRealm() {
        return realm;
    }
}
