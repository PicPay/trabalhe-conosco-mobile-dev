package br.com.everaldocardosodearaujo.picpay.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.everaldocardosodearaujo.picpay.App.DatabaseApp;
import br.com.everaldocardosodearaujo.picpay.Object.CreditCardObject;

/**
 * Created by E. Cardoso de Araújo on 15/03/2018.
 */

public class CreditCardRepository {
    private SQLiteDatabase DB_PicPay;
    private String TB_CREDIT_CARD = "CREDIT_CARD";

    private enum FIELDS{
        ID,
        FLAG,
        NAME,
        NUMBER_CARD,
        VALIDITY,
        CCV
    }

    public CreditCardRepository(Context context){
        DatabaseApp databaseApp = new DatabaseApp(context);
        this.DB_PicPay = databaseApp.getWritableDatabase();
    }

    public long insert(CreditCardObject creditCard){
        try{
            ContentValues contentValues = new ContentValues();

            contentValues.put(FIELDS.ID.name(),creditCard.getId());
            contentValues.put(FIELDS.FLAG.name(),creditCard.getFlag());
            contentValues.put(FIELDS.NAME.name(),creditCard.getName());
            contentValues.put(FIELDS.NUMBER_CARD.name(),creditCard.getNumberCard());
            contentValues.put(FIELDS.VALIDITY.name(),creditCard.getValidity());
            contentValues.put(FIELDS.CCV.name(),creditCard.getCcv());

            return this.DB_PicPay.insert(this.TB_CREDIT_CARD,null,contentValues);
        }catch (Exception e){
            return 0;
        }
    }

    public Boolean update(CreditCardObject creditCard){
        try{
            ContentValues contentValues = new ContentValues();

            contentValues.put(FIELDS.ID.name(),creditCard.getId());
            contentValues.put(FIELDS.FLAG.name(),creditCard.getFlag());
            contentValues.put(FIELDS.NAME.name(),creditCard.getName());
            contentValues.put(FIELDS.NUMBER_CARD.name(),creditCard.getNumberCard());
            contentValues.put(FIELDS.VALIDITY.name(),creditCard.getValidity());
            contentValues.put(FIELDS.CCV.name(),creditCard.getCcv());

            this.DB_PicPay.update(this.TB_CREDIT_CARD,contentValues,FIELDS.ID.name() + " = ? ",
                    new String[]{Long.toString(creditCard.getId())});
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public Boolean delete(CreditCardObject creditCard){
        try{
            this.DB_PicPay.delete(this.TB_CREDIT_CARD,FIELDS.ID.name() + " = ? ",
                    new String[]{Long.toString(creditCard.getId())});
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public List<CreditCardObject> select(){
        try{
            List<CreditCardObject> list = new ArrayList<CreditCardObject>();
            String[] columns = new String[]{
                    FIELDS.ID.name(),
                    FIELDS.FLAG.name(),
                    FIELDS.NAME.name(),
                    FIELDS.NUMBER_CARD.name(),
                    FIELDS.VALIDITY.name(),
                    FIELDS.CCV.name()};

            Cursor cursor = this.DB_PicPay.query(this.TB_CREDIT_CARD,columns,null,null,null,null,null);

            if (cursor.getCount()>0){
                cursor.moveToFirst();
                CreditCardObject creditCard;
                do{
                    creditCard = new CreditCardObject();
                    creditCard.setId(cursor.getLong(0));
                    creditCard.setFlag(cursor.getString(1));
                    creditCard.setName(cursor.getString(2));
                    creditCard.setNumberCard(cursor.getString(3));
                    creditCard.setValidity(cursor.getString(4));
                    creditCard.setCcv(cursor.getString(5));
                    list.add(creditCard);
                }while (cursor.moveToNext());
            }
            return list;
        }catch (Exception ex){
            return null;
        }
    }
}
