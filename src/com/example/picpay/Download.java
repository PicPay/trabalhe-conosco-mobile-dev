package com.example.picpay;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;


	class Download extends AsyncTask<Void, Void, String> {
		private DBController DB;
		private Context context;
		
		public Download(DBController DB,Context context,AsynResponse asynResponse){
			this.DB = DB;
			this.setContext(context);
			this.asynResponse = asynResponse;
		}
		
		public interface AsynResponse {
	        void processFinish(Boolean output);
	    }

	    AsynResponse asynResponse = null;

	    @Override
	    protected String doInBackground(Void... params) {
	        HttpURLConnection urlConnection = null;
	        BufferedReader reader = null;
	        try {
	            URL url = new URL("http://careers.picpay.com/tests/mobdev/users");
	            urlConnection = (HttpURLConnection) url.openConnection();
	            urlConnection.setRequestMethod("GET");
	            urlConnection.connect();

	            InputStream inputStream = urlConnection.getInputStream();

	            reader = new BufferedReader(new InputStreamReader(inputStream));

	            String linha;
	            StringBuffer buffer = new StringBuffer();
	            while((linha = reader.readLine()) != null) {
	                buffer.append(linha);
	                //buffer.append("\n");
	            }
	            
	            insere(buffer.toString());

	            return buffer.toString();
	        } catch (Exception e) {
	            e.printStackTrace();
	            if (urlConnection != null) {
	                urlConnection.disconnect();
	            }

	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                    e1.printStackTrace();
	                }
	            }
	        }

	        return null;
	    }

	    @Override
	    protected void onPostExecute(String json) {
	    	super.onPostExecute(json);
	        asynResponse.processFinish(true);
	    }
	    
	    public void insere(String json) throws JSONException{

	    	 ContentValues cv = new  ContentValues();
			 JSONArray jsonArray = new JSONArray(json);
			 JSONObject jsonObj;

			 for (int i = 0; i < jsonArray.length(); i++) {
				 
				jsonObj = new JSONObject(jsonArray.getString(i));
				
			    cv.put("ID", jsonObj.getInt("id"));
			    cv.put("NAME", jsonObj.getString("name"));
			    cv.put("IMG", getBytes(baixarImagem(jsonObj.getString("img"))));
			    cv.put("USERNAME", jsonObj.getString("username"));
			    
			    DB.onInsertOrUpdate("MOB_CADA", cv);
			}
	    }
	    
	    public byte[] getBytes(Bitmap bitmap) {
	        ByteArrayOutputStream stream = new ByteArrayOutputStream();
	        bitmap.compress(CompressFormat.PNG, 0, stream);
	        return stream.toByteArray();
	    }
	    
	    private Bitmap baixarImagem(String url) {
	        try{

	        	InputStream input = new java.net.URL(url).openStream();
	        	Bitmap bitmap = BitmapFactory.decodeStream(input);

	        	return bitmap;
	        }catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

		public Context getContext() {
			return context;
		}

		public void setContext(Context context) {
			this.context = context;
		}
	}
