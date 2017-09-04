package br.com.dalcim.picpay.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.dalcim.picpay.data.CreditCard;

/**
 * @author Wiliam
 * @since 03/09/2017
 */

public class CreditCardDao extends BaseDao {
    public CreditCardDao(Context context) {
        super(context);
    }

    public boolean insert(CreditCard creditCard) {
        boolean sucess = true;
        SQLiteDatabase db = getWritableDatabase();

        try {
            ContentValues cv = objectToContentValues(creditCard);
            db.insert(DB.CREDIT_CARD.TABLE_NAME, null, cv);
        } catch (Exception e){
            sucess = false;
        }finally {
            db.close();
        }

        return sucess;
    }

    public List<CreditCard> getList(){

        List<CreditCard> cards;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(DB.CREDIT_CARD.TABLE_NAME, null, null, null, null, null, null);
            cards = cursorToList(cursor);
        }catch (Exception e){
            cards = Collections.EMPTY_LIST;
        }finally {
            db.close();
            cursor.close();
        }

        return cards;
    }

    private ContentValues objectToContentValues(CreditCard creditCard){
        ContentValues cv = new ContentValues();
        cv.put(DB.CREDIT_CARD.CARD_NUMBER, creditCard.getCardNumber());
        cv.put(DB.CREDIT_CARD.EXPIRY_DATE, creditCard.getExpiryDate());
        cv.put(DB.CREDIT_CARD.CVV, creditCard.getCvv());
        return cv;
    }

    private List<CreditCard> cursorToList(Cursor cursor){
        List<CreditCard> creditCards = new ArrayList(cursor.getCount());
        if(cursor.moveToNext()){
            creditCards.add(cursorToObject(cursor));
        }

        return creditCards;
    }

    private CreditCard cursorToObject(Cursor cursor){
        CreditCard creditCard = new CreditCard();

        creditCard.setCardNumber(cursor.getString(cursor.getColumnIndex(DB.CREDIT_CARD.CARD_NUMBER)));
        creditCard.setExpiryDate(cursor.getString(cursor.getColumnIndex(DB.CREDIT_CARD.EXPIRY_DATE)));
        creditCard.setCvv(cursor.getInt(cursor.getColumnIndex(DB.CREDIT_CARD.CVV)));

        return creditCard;
    }
}
