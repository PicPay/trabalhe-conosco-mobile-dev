package br.com.gsas.app.picpay.Connection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import br.com.gsas.app.picpay.DAO.FeedDAO;
import br.com.gsas.app.picpay.Database.DatabaseManager;
import br.com.gsas.app.picpay.Domain.Feed;

public class ServiceFeed {


    private FeedDAO db;
    private Context context;


    public ServiceFeed(Context context){

        this.context = context;

        db = DatabaseManager.getInstance(context).getFeedDAO();

    }

    @SuppressLint("StaticFieldLeak")
    public void getAllBD(final MyCallback<Feed> callback){

        new AsyncTask<Void, Void, List<Feed>>(){

            @Override
            protected List<Feed> doInBackground(Void... voids) {

                Log.d("Feed", "getAllBD no BD");

                return db.getAll();
            }

            @Override
            protected void onPostExecute(List<Feed> feeds) {

                if(feeds == null){

                    callback.failure("Retorno null");
                }
                else{

                    if(feeds.size() == 0){
                        callback.empty();
                    }
                    else {
                        callback.sucess(feeds);
                    }
                }
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void insertBD(final OnCallback<Feed> callback, final Feed feed){

        new AsyncTask<Void, Void, Feed>(){

            @Override
            protected Feed doInBackground(Void... voids) {

                Log.d("Feed", "Salvando no BD");


                Feed atualiza = db.findById(feed.getId());

                if( atualiza == null){

                    db.insert(feed);
                }
                else{
                    db.update(feed);
                }
                return feed;
            }

            @Override
            protected void onPostExecute(Feed feed) {

                callback.sucess(feed);
            }
        }.execute();
    }
}
