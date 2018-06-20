package br.com.gsas.app.picpay.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import br.com.gsas.app.picpay.DAO.CartaoDAO;
import br.com.gsas.app.picpay.DAO.FeedDAO;
import br.com.gsas.app.picpay.Domain.Cartao;
import br.com.gsas.app.picpay.Domain.Feed;
import br.com.gsas.app.picpay.Util.ConverterContato;

@Database(entities = {Cartao.class, Feed.class}, version = 1, exportSchema = false)
@TypeConverters({ConverterContato.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract CartaoDAO cartaoDAO();

    public abstract FeedDAO feedDAO();

}
