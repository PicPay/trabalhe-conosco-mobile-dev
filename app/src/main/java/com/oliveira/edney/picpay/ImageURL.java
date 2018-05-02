package com.oliveira.edney.picpay;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/* Classe para captura de imagens por url */
public class ImageURL extends AsyncTask<String, Integer, Bitmap>{

    private Callback callback;

    public interface Callback{
        void onLoaded(Bitmap bitmap);
    }

    public ImageURL(Callback callback){

        this.callback = callback;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {

        URL url;
        Bitmap bitmap = null;
        HttpURLConnection conexao = null;

        try {

            url = new URL(urls[0]);
            conexao = (HttpURLConnection) url.openConnection();

            if (conexao.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream inputS = conexao.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputS);
            }

        } catch (Exception e) {
            Log.e("MeuErro", e.getMessage());
        } finally {

            if (conexao != null) {
                conexao.disconnect();
            }
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        callback.onLoaded(bitmap);
    }
}
