package mobile.picpay.com.br.picpaymobile.migration;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by johonatan on 07/10/2017.
 */

public class MyMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        RealmSchema schema = realm.getSchema();

//        if (oldVersion == 1) {
//            schema.get("Pessoa").addField("teste",Double.class);
//
//            oldVersion++;
//        }

    }
}
