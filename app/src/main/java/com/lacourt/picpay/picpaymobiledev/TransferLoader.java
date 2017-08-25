package com.lacourt.picpay.picpaymobiledev;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by igor on 20/08/2017.
 */

public class TransferLoader extends AsyncTaskLoader<List<String>> {

    //    private static final String LOG_TAG = EarthquakeLoader.class.getSimpleName();
    private static final String LOG_TAG = "LOG_TAG";
    private String mUrl = "";
    private TransferData transferData;
    private List<String> data;

    public TransferLoader(Context context, String url, TransferData transferData) {
        super(context);
        this.mUrl = url;
        this.transferData = transferData;
        this.data = new ArrayList<String>();
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<String> loadInBackground() {
        if(mUrl == null || mUrl.isEmpty()){
            return null;
        }
        // Perform the HTTP request for earthquake data and process the response.
        InputStream inputStream = null;
        String jsonResponse = "";

        try {
            URL url = new URL("http://careers.picpay.com/tests/mobdev/transaction"); //Enter URL here
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
            httpURLConnection.setRequestProperty("Content-Type", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
            httpURLConnection.connect();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("card_number", transferData.getCardNumber());
            jsonObject.put("cvv", transferData.getCvv());
            jsonObject.put("value", transferData.getValue());
            jsonObject.put("expiry_date", transferData.getExpiryDate());
            jsonObject.put("destination_user_id", transferData.getDestinationUserId());

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(jsonObject.toString());
            wr.flush();
            wr.close();

            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = QueryUtils.readFromStream(inputStream);

//                Log.d(AsyncPost.class.getSimpleName(), "posttest: " + httpURLConnection.getResponseCode());
//                Log.d(AsyncPost.class.getSimpleName(), "posttest: " + inputStream);
//                Log.d(AsyncPost.class.getSimpleName(), "posttest: " + jsonResponse);

                JSONObject jsonResponseObject = new JSONObject(jsonResponse);
                data.add(0, jsonResponseObject.getJSONObject("transaction").getJSONObject("destination_user")
                        .getString("name"));
                data.add(1, jsonResponseObject.getJSONObject("transaction").getJSONObject("destination_user")
                        .getString("img"));
                data.add(2, jsonResponseObject.getJSONObject("transaction").getString("status"));

//                Log.d(AsyncPost.class.getSimpleName(), "posttest: " + data.get(0));
//                Log.d(AsyncPost.class.getSimpleName(), "posttest: " + data.get(1));
//                Log.d(AsyncPost.class.getSimpleName(), "posttest: " + data.get(2));

            } else {
//                Log.e(AsyncPost.class.getSimpleName(), "Error response code: " + httpURLConnection.getResponseCode());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }
}
