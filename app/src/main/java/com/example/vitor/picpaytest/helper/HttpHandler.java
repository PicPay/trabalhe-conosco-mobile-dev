package com.example.vitor.picpaytest.helper;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Vitor on 19/08/2017.
 */

//Classe auxiliar na comunicação HTTP
public class HttpHandler {

    private static final String TAG = HttpHandler.class.getSimpleName();

    public HttpHandler(){

    }

    public String buscaUrl(String reqUrl){
        String response = null;

        try{
            //Monta conexão com o método GET para recuperar o JSON
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            //Lê a resposta
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException" + e.getMessage());
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedProtocolException" + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException" + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception" + e.getMessage());
        }

        return response;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;

        try{
            while((line = reader.readLine()) != null){
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
