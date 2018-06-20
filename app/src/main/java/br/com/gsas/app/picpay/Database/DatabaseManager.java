package br.com.gsas.app.picpay.Database;

import android.arch.persistence.room.Room;
import android.content.Context;

import br.com.gsas.app.picpay.DAO.CartaoDAO;
import br.com.gsas.app.picpay.DAO.FeedDAO;

public class DatabaseManager {

    private AppDatabase dbInstance;

    public static DatabaseManager getInstance(Context context) {

        return new DatabaseManager(context);
    }

    private DatabaseManager(Context context) {

        dbInstance = Room.databaseBuilder(context, AppDatabase.class, "picpay.sqlite").build();
    }

    public CartaoDAO getCartaoDAO(){

        return dbInstance.cartaoDAO();
    }

    public FeedDAO getFeedDAO(){

        return dbInstance.feedDAO();
    }

}
