package gilianmarques.dev.picpay_test.crud;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import gilianmarques.dev.picpay_test.BuildConfig;
import gilianmarques.dev.picpay_test.models.CreditCard;
import gilianmarques.dev.picpay_test.utils.MyApp;

/**
 * Helper pra trabalhar comm {@link SQLiteDatabase}
 * O padrão Singleton mantem apenas uma única instancia do DB rodando evitando exceptions
 */
public class Database extends SQLiteOpenHelper {

    private static final Database instance = new Database();
    private static final String TAG = Database.class.getSimpleName();

    public Database() {
        super(MyApp.getContext(), BuildConfig.APPLICATION_ID.concat(".sqlite"), null, 1);
    }


    public static Database getInstance() {
        return instance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(MyApp.getContext());


        if (mPreferences.getBoolean("fist_time", true)) {
            createCardsTable(db);
            SharedPreferences.Editor mEditor = mPreferences.edit();
            mEditor.putBoolean("first_time", false).apply();
            Log.i(TAG, "onCreate: Tabela de cartoes criada");
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    private void createCardsTable(SQLiteDatabase db) {
        /*Constantes seriam usadas em um sistema mais robusto*/
        db.execSQL("CREATE TABLE IF NOT EXISTS CARDS (NUMBER INTEGER, CVV INTEGER, EXPIRE_DATE TEXT,OWNER_NAME TEXT,BRAND TEXT)");
    }

    /**
     * @param mCreditCard o {@link CreditCard} a ser inserido no DB
     * @return um boolean como resultado de sucesso ou falha na operação
     */
    public boolean insertCard(CreditCard mCreditCard) {

        ContentValues mValues = new ContentValues();

        mValues.put("NUMBER", mCreditCard.getNumber());
        mValues.put("CVV", mCreditCard.getCvv());
        mValues.put("EXPIRE_DATE", mCreditCard.getExpireDate());
        mValues.put("OWNER_NAME", mCreditCard.getOwnerName());
        mValues.put("BRAND", mCreditCard.getBrand());

        return getWritableDatabase().insert("CARDS", null, mValues) != -1;
    }

    /**
     * @param mCreditCard o {@link CreditCard} a ser removido  do DB
     * @return um boolean como resultado de sucesso ou falha na operação
     * <p>
     * N foi pedido mas foi implementado para fins de teste
     */
    public boolean removeCard(CreditCard mCreditCard) {
        String whereClause = "NUMBER = '" + mCreditCard.getNumber() + "' AND CVV = '" + mCreditCard.getCvv() + "'";
        return getWritableDatabase().delete("CARDS", whereClause, null) > 0;
    }

    public @NonNull
    ArrayList<CreditCard> getCards() {
        ArrayList<CreditCard> mCards = new ArrayList<>();

        String query = "SELECT * FROM CARDS";

        Cursor mCursor = getWritableDatabase().rawQuery(query, null);

        if (mCursor.moveToFirst()) {
            do {

                CreditCard mCard = new CreditCard();

                mCard.setNumber(mCursor.getLong(0));
                mCard.setCvv(mCursor.getInt(1));
                mCard.setExpireDate(mCursor.getString(2));
                mCard.setOwnerName(mCursor.getString(3));
                mCard.setBrand(mCursor.getString(4));
                mCards.add(mCard);

            } while (mCursor.moveToNext());
        }
        mCursor.close();
        return mCards;
    }

}
