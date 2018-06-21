package com.example.eduardo.demoapppagamento.payment_processing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentProcActivity extends AppCompatActivity {

    private Contact mRecipient;
    private Card mCreditCard;
    private Double mValue;

    private LinearLayout mReportLayout;
    private TextView mStatusText;
    private ProgressBar mProgressSpinner;
    private TextView mErrorText;

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

        mErrorText = (TextView) findViewById(R.id.payment_error_text);
        mErrorText.setVisibility(TextView.INVISIBLE);

        Intent intent = getIntent();
        if (intent.hasExtra("recipient")){
            mRecipient = (Contact) intent.getSerializableExtra("recipient");
        }
        if (intent.hasExtra("card")){
            mCreditCard = (Card) intent.getSerializableExtra("card");
        }
        if (intent.hasExtra("value")){
            mValue = intent.getDoubleExtra("value", 0);
        }

        JSONObject json = buildJson(mValue, mRecipient, mCreditCard);
        //String jsonStr = jsonRequest.toString().replaceAll("\\\\", "");
        Log.d("JSON", json.toString());

        JsonObjectRequest jsonRequest = buildJsonRequest(json);

        RequestQueue queue = RequestQueueSingleton.getInstance().getRequestQueue();
        queue.add(jsonRequest);
    }

    private JSONObject buildJson(Double value, Contact recipient, Card creditCard) {

        String cardNumber = creditCard.getNumber();
        int cvv = creditCard.getCvv();
        String expiry_month = Integer.toString(creditCard.getExpiryMonth());
        String expiry_year = Integer.toString(creditCard.getExpiryYear());
        String expiry_date = expiry_month +"/"+ expiry_year;
        Log.d("concat",expiry_date);

        JSONObject json = new JSONObject();
        try {
            json.put("card_number",cardNumber);
            json.put("cvv", cvv);
            json.put("value", value);
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
                        //Log.d("post", response.toString());
                        parseResponse(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("postError", error.toString());
                        showResponse(0, 0, false, "Erro de conexão");
                    }
                });

        return jsonObjectRequest;
    }


    private void parseResponse(JSONObject response) {
        int transactionId = 0;
        int timestamp = 0;
        boolean success = false;
        String status = "";

        try {
            JSONObject transaction = response.getJSONObject("transaction");
            transactionId = transaction.getInt("id");
            timestamp = transaction.getInt("timestamp");
            success = transaction.getBoolean("success");
            status = transaction.getString("status");
        } catch (JSONException e ) {
            e.printStackTrace();
        }

        showResponse(transactionId, timestamp, success, status);
    }


    private void showResponse(int transactionId, int timestamp, boolean success, String status) {

        mProgressSpinner.setVisibility(ProgressBar.GONE);
        setActionButtons();

        if (success) {

            TextView transactinText = (TextView) findViewById(R.id.payment_transaction_text);
            TextView dateText = (TextView) findViewById(R.id.payment_date_text);
            TextView cardText = (TextView) findViewById(R.id.payment_card_text);
            TextView valueText = (TextView) findViewById(R.id.payment_value_text);

            transactinText.setText(String.valueOf(transactionId));

            Date dateTime = new java.util.Date((long)timestamp*1000);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
            dateText.setText(dateFormat.format(dateTime));

            String cardNumLastFour = mCreditCard.getNumber().substring(12);
            cardText.setText("**** **** **** " + cardNumLastFour);

            NumberFormat nf = NumberFormat.getCurrencyInstance();
            String value = nf.format(mValue);
            valueText.setText(value);

            mStatusText.setText("Pagamento confirmado!");
            mReportLayout.setVisibility(LinearLayout.VISIBLE);
        }
        else {
            String errorMsg = "Status: " + status + "\n\n" +
                    "Infelizmente não pudemos concluir o pagamento. " +
                    "Verifique sua conexão e as informações de pagamento ou tente novamente mais tarde.";
            mErrorText.setText(errorMsg);
            mErrorText.setVisibility(TextView.VISIBLE);

            mStatusText.setText("Erro: pagamento não efetuado!");
            mStatusText.setTextColor(getResources().getColor(R.color.colorError));
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
}
