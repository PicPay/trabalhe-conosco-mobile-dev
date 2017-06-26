package com.example.picpay;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

public class DBController {
  
    private SQLiteDatabase db;
    private DataBase banco;
    private SQLiteStatement insertMobCada;
  
    public DBController(Context context){
        banco = new DataBase(context);
        this.db = banco.getWritableDatabase();
    }
  
    public long onInsert(String tabela,ContentValues valores) {
		return this.db.insert(tabela,null,valores);
	}
    
    public void onInsertOrUpdate(String tabela,ContentValues valores) {
    	Cursor cIU = this.onSelect("select id from "+tabela+" where id="+valores.getAsInteger("ID"));
    	if(cIU!=null){
    		if(cIU.getCount()>0){
    			this.db.update(tabela, valores, "id="+valores.getAsInteger("ID"), null);
    		}else{
    			this.db.insert(tabela,null,valores);
    		}
    	}
	}
    
    public long onUpdateOrInsert(String tabela,ContentValues valores) {
    	long ret = -1;
    	Cursor cIU = this.onSelect("select id from "+tabela+" where id="+valores.getAsInteger("ID"));
    	if(cIU!=null){
    		if(cIU.getCount()>0 && valores.getAsInteger("ID")>0){
    			ret = this.db.update(tabela, valores, "id="+valores.getAsInteger("ID"), null);
    		}else{
    			valores.remove("ID");
    			ret = this.db.insert(tabela,null,valores);
    		}
    	}
    	return ret;
	}

    public void onCreateTables() {
    	banco.onCreate(this.db);
	}
    public void onDeleteTables() {
	    this.db.execSQL("DROP TABLE IF EXISTS MOB_CADA");
	}
    
    public Cursor onSelect(String sql){
    	Cursor cursor=null;
    	if(this.db !=null){
		   cursor = this.db.rawQuery(sql, null);
    	}
		return cursor;
	}
    
    public void onDelete(String tabela, ContentValues valores){
        db.delete(tabela,"id="+valores.getAsInteger("ID"),null);
    }
    
    public void onExecute(String sql){

		this.db.execSQL(sql.toString());
	}

    public String insertMobCada(String []v){
       this.insertMobCada 	 = this.db.compileStatement("INSERT INTO MOB_CADA (ID,NAME,IMG,USERNAME) values(?,?,?,?)");
	   for(int i=1;i<(v.length);i++){
		   this.insertMobCada.bindString(i, v[i]);
	   }
	   this.insertMobCada.executeInsert();
	   return v[1];
	   
	}
}
