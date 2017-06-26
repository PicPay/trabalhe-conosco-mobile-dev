package com.example.picpay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONObject;
import android.content.Context;
import android.os.AsyncTask;

class Upload extends AsyncTask<Void, Void, String> {
	private DBController DB;
	private Context context;
	private JSONObject jsonParam;
	
	public Upload(DBController DB,Context context,AsynResponse asynResponse,JSONObject jsonParam){
		this.setDB(DB);
		this.setContext(context);
		this.asynResponse = asynResponse;
		this.jsonParam = jsonParam;
	}
	
	public interface AsynResponse {
        void processFinish(Boolean output);
        void processReturn(String output);
    }

    AsynResponse asynResponse = null;

    @Override
    protected String doInBackground(Void... params) {
        HttpURLConnection urlConnection = null;
        StringBuilder sb = new StringBuilder(); 
        try {  
            URL url = new URL("http://careers.picpay.com/tests/mobdev/transaction");  
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);   
            urlConnection.setRequestMethod("POST");  
            urlConnection.setUseCaches(false);  
            urlConnection.setConnectTimeout(10000);  
            urlConnection.setReadTimeout(10000);  
            urlConnection.setRequestProperty("Content-Type","application/json");   
            urlConnection.connect();  

  
            OutputStream os = urlConnection.getOutputStream();
            os.write(jsonParam.toString().getBytes("UTF-8"));
            os.close();
            

            int HttpResult =urlConnection.getResponseCode();  
            if(HttpResult ==HttpURLConnection.HTTP_OK){  
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));  
                String line = null;  
                while ((line = br.readLine()) != null) {  
                    sb.append(line + "\n");  
                }  
                br.close();  

                return sb.toString();

            }else{  
            	return urlConnection.getResponseMessage();
            }  
        } catch (MalformedURLException e) {  
              e.printStackTrace();  
        }  
        catch (IOException e) {  
            e.printStackTrace();  
        }finally{  
            if(urlConnection!=null)  
            	urlConnection.disconnect();  
        }  

        return null;
    }

    @Override
    protected void onPostExecute(String json) {
    	super.onPostExecute(json);
    	
        asynResponse.processFinish(true);
        asynResponse.processReturn(json);
    }
    
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public DBController getDB() {
		return DB;
	}

	public void setDB(DBController dB) {
		DB = dB;
	}
}
