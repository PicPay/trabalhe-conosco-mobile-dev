package com.example.eduardo.demoapppagamento.payment_processing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.eduardo.demoapppagamento.R;
import com.example.eduardo.demoapppagamento.contactslist.ContactsListActivity;
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

    private LinearLayout mReportLayout;
    private TextView mStatusText;
    private ProgressBar mProgressSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_proc_layout);
        setTitle("Pagamento");

        mReportLayout = (LinearLayout) findViewById(R.id.transaction_report_layout);
        mReportLayout.setVisibility(LinearLayout.INVISIBLE);

        mProgressSpinner = (ProgressBar) findViewById(R.id.payment_progress_bar);
        mProgressSpinner.setVisibility(ProgressBar.VISIBLE);

        mStatusText = (TextView) findViewById(R.id.payment_status_text);
        mStatusText.setText("Processando");

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

        JSONObject json = buildJson(mValue, mRecipient, mCreditCard);
        //String jsonStr = jsonRequest.toString().replaceAll("\\\\", "");
        Log.d("JSON", json.toString());

        JsonObjectRequest jsonRequest = buildJsonRequest(json);

        RequestQueue queue = RequestQueueSingleton.getInstance().getRequestQueue();
        queue.add(jsonRequest);
    }

    private JSONObject buildJson(String value, Contact recipient, Card creditCard) {

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


    private JsonObjectRequest buildJsonRequest(JSONObject json) {

        String url = "http://careers.picpay.com/tests/mobdev/transaction";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, json, new Response.Listener<JSONObject>() {

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

        return jsonObjectRequest;
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


        showResponse(transactionId, timestamp, success);
    }


    private void showResponse(int transactionId, int timestamp, boolean success) {

        mProgressSpinner.setVisibility(ProgressBar.GONE);
        setActionButtons();

        if (success) {

            // set report

            mStatusText.setText("Pagamento confirmado!");
            mReportLayout.setVisibility(LinearLayout.VISIBLE);
        }
        else {
            // Error message
        }
    }


    private void setActionButtons() {
        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentProcActivity.this, ContactsListActivity.class );
                intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(intent);
            }
        });

        Button payAgainButton = (Button) findViewById(R.id.pay_again_button);
        payAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // get queue
    // start
}
