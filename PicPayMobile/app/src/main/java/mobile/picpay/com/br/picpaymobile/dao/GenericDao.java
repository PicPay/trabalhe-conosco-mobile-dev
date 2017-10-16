package mobile.picpay.com.br.picpaymobile.dao;

import android.app.Activity;
import android.content.Context;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public abstract class GenericDao<E extends RealmObject> {
    private Class<E> type;
    private Activity activity;
    private Context context;
    private Integer idAtual;
    private Realm realm;

    public GenericDao(Activity context, Class<E> type) {
        this.type = type;
        this.activity = context;
        this.realm = Realm.getDefaultInstance();
    }

    public GenericDao(Context context, Class<E> type) {
        this.type = type;
        this.context = context;
        this.realm = Realm.getDefaultInstance();
    }

    public RealmResults<E> getAll() {
        RealmResults<E> result;
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<E> query = realm.where(type);
        result = query.findAll();

        return result;
    }

    public E getById(final Integer id) {
        if (id != null) {
            E result;
            Realm realm = Realm.getDefaultInstance();

            RealmQuery<E> query = realm.where(type);
            result = query.equalTo("id", id).findFirst();

            return result;
        }
        return null;
    }

    public E getById(final Long id) {
        if (id != null) {
            E result;
            Realm realm = Realm.getDefaultInstance();

            RealmQuery<E> query = realm.where(type);
            result = query.equalTo("id", id).findFirst();

            return result;
        }
        return null;
    }

    public E save(final E obj) {
        final E[] e = (E[]) new RealmObject[]{obj};
        if (obj != null) {
            Realm realm = Realm.getDefaultInstance();
            try {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        e[0] = realm.copyToRealmOrUpdate(obj);
                    }
                });
            } finally {
                realm.close();
            }
        } else {
            //GenericActivity.msg("Impossivel gravar objeto nulo! " + e, ERROR, null, this.getClass().getName());
        }
        return e[0];
    }

    public void save(final E obj, Realm realm) {
        if (obj != null) {
            try {
                realm.copyToRealmOrUpdate(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            new Exception("Impossivel gravar objeto nulo!").getMessage();
        }
    }

    public void saveAll(final List<E> listObj) {
        try {
            if (listObj != null) {
                try {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.copyToRealmOrUpdate(listObj);
                        }
                    });
                } finally {
                    realm.close();
                }
            } else {
                new Exception("Impossivel gravar objeto nulo!").getMessage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteById(Integer id) {
        if (id != null) {
            final RealmResults<E> results = realm.where(type).equalTo("id", id).findAll();

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    results.deleteAllFromRealm();
                }
            });

        }
    }

    public void deleteAll() {
        final RealmResults<E> results = realm.where(type).findAll();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    results.deleteAllFromRealm();
                }
            });
        } finally {
            realm.close();
        }
    }

    public boolean idExists(Integer id) {
        if (id != null) {
            final RealmResults<E> results = realm.where(type).equalTo("id", id).findAll();
            return results.isEmpty();
        }
        return false;
    }

    public Integer getNextId() {
        this.idAtual = (realm.where(type).max("id")) == null ? 1 : (realm.where(type).max("id").intValue() + 1);
        return this.idAtual;
    }

    public E novo() {
        E ficha = realm.createObject(type, getNextId());
        return ficha;
    }

    public E novo(Realm realm) {
        E ficha = realm.createObject(type, getNextId());
        return ficha;
    }

}
