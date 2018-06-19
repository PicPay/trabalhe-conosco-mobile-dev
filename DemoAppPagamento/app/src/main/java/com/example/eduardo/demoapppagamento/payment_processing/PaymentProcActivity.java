package com.example.eduardo.demoapppagamento.payment_processing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.eduardo.demoapppagamento.R;
import com.example.eduardo.demoapppagamento.data.Card;
import com.example.eduardo.demoapppagamento.data.Contact;
import com.example.eduardo.demoapppagamento.util.RequestQueueSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;

public class PaymentProcActivity extends AppCompatActivity {

    private Contact mRecipient;
    private Card mCreditCard;
    private String mValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_proc_layout);
        setTitle("Pagamento");

        Intent intent = getIntent();
        if (intent.hasExtra("recipient")){
            mRecipient = (Contact) intent.getSerializableExtra("recipient");
        }
        if (intent.hasExtra("card")){
            mCreditCard = (Card) intent.getSerializableExtra("card");
        }
        if (intent.hasExtra("value")){
            mValue = intent.getStringExtra("value");
        }

        JSONObject jsonRequest = buildJsonRequest(mValue, mRecipient, mCreditCard);
        //String jsonStr = jsonRequest.toString().replaceAll("\\\\", "");


        Log.d("JSON", jsonRequest.toString());


        String url = "http://careers.picpay.com/tests/mobdev/transaction";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, jsonRequest, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                        //mTextView.setText("Response: " + response.toString());
                        Log.d("post", response.toString());
                        parseResponse(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        //Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        Log.d("postError", error.toString());
                    }
                });

        RequestQueue queue = RequestQueueSingleton.getInstance().getRequestQueue();
        queue.add(jsonObjectRequest);

    }

    private static JSONObject buildJsonRequest(String value, Contact recipient, Card creditCard) {

        String cardNumber = creditCard.getNumber();
        int cvv = creditCard.getCvv();
        String expiry_month = Integer.toString(creditCard.getExpiryMonth());
        String expiry_year = Integer.toString(creditCard.getExpiryYear());
        String expiry_date = expiry_month +"/"+ expiry_year;
        Log.d("concat",expiry_date);
        Double valueDouble;
        try {
            valueDouble = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }

        JSONObject json = new JSONObject();
        try {
            json.put("card_number",cardNumber);
            json.put("cvv", cvv);
            json.put("value", valueDouble);
            json.put("expiry_date", expiry_date);
            json.put("destination_user_id", recipient.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }


    private void parseResponse(JSONObject response) {
        int transactionId = 0;
        int timestamp = 0;
        boolean success = false;

        try {
            JSONObject transaction = response.getJSONObject("transaction");
            transactionId = transaction.getInt("id");
            timestamp = transaction.getInt("timestamp");
            success = transaction.getBoolean("success");
        } catch (JSONException e ) {
            e.printStackTrace();
        }

        Log.d("parseJson", "id: " + transactionId + ", timestamp: " + timestamp + ", success: "+ success);


    }

    // get queue
    // start
}
