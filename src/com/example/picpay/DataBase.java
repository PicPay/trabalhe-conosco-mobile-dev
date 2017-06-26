package com.example.picpay;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBase extends SQLiteOpenHelper{

    public static final String DATABASE_NAME="MOB_DATABASE";
    private static final int DATABASE_VERSION=1;

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static interface Tabelas{
        String MOB_CADA="MOB_CADA";
        String MOB_CART="MOB_CART";   
        String MOB_TRANS="MOB_TRANS"; 
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    	StringBuilder mobCada = new StringBuilder();
    	mobCada.append(" CREATE TABLE IF NOT EXISTS "+Tabelas.MOB_CADA+"(");
    	mobCada.append(" ID INTEGER PRIMARY KEY NOT NULL,");
    	mobCada.append(" NAME varchar(255),");
    	mobCada.append(" IMG BLOB,");
    	mobCada.append(" USERNAME varchar(5))");
    	
    	
    	StringBuilder mobCart = new StringBuilder();
    	mobCart.append(" CREATE TABLE IF NOT EXISTS "+Tabelas.MOB_CART+"(");
    	mobCart.append(" ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,");
    	mobCart.append(" CL INTEGER,");
    	mobCart.append(" NUM varchar(16),");
    	mobCart.append(" CVV INTEGER,");
    	mobCart.append(" VEN varchar(5),");
    	mobCart.append(" VAL NUMERIC(18,2))");
    	//mobCart.append(" CONSTRAINT FK_CADA__CART FOREIGN KEY (CL) REFERENCES MOB_CADA (ID) ON UPDATE CASCADE ON DELETE NO ACTION");
    	
    	StringBuilder mobTrans = new StringBuilder();
    	mobTrans.append(" CREATE TABLE IF NOT EXISTS "+Tabelas.MOB_TRANS+"(");
    	mobTrans.append(" ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,");
    	mobTrans.append(" CL INTEGER,");
    	mobTrans.append(" TRANS_ID INTEGER,");
    	mobTrans.append(" TSTAMP varchar(50),");
    	mobTrans.append(" STATUS varchar(10),");
    	mobTrans.append(" VAL NUMERIC(18,2))");
    	
    	
        //Criar tabela de cadastro
        db.execSQL(mobCada.toString());
        
        //Criar tabela de cartão
        db.execSQL(mobCart.toString());
        
      //Criar tabela de transações
        db.execSQL(mobTrans.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
    	db.execSQL("DROP TABLE IF EXISTS "+Tabelas.MOB_CADA);
    	onCreate(db);
    }
}
