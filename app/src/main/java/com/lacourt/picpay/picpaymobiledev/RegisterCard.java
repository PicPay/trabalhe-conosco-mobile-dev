package com.lacourt.picpay.picpaymobiledev;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pinball83.maskededittext.MaskedEditText;
import com.lacourt.picpay.picpaymobiledev.DAO.CardDAO;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RegisterCard extends AppCompatActivity{
    EditText transferValue;
    EditText cardNumber;
    MaskedEditText expiryDate;
    EditText cvv;

    LinearLayout layoutValue;
    LinearLayout layoutCard;
    LinearLayout layoutCardRegistered;
    LinearLayout layoutSuccess;

    Button btnValueOk;
    Button btnFinishTransfer;
    Button btnFinishTransferCardRegistered;
    Button btnRemoveCard;
    Button btnSuccess;

    TextView txtSuccess;
    TextView txtSuccesName;
    ImageView imgSuccess;

    Card card;
    CardDAO cardDAO;
    TransferData transferData;
    String userId;

    public static final String REQUEST_URL = "http://careers.picpay.com/tests/mobdev/transaction";
    public static final int TRANSFER_LOADER_ID = 2;

    private boolean isLayoutCardRegistered;

    AsyncPost asyncPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_card);

        Intent intent = getIntent();
        userId = intent.getStringExtra("UserId");

        transferData = new TransferData();

        transferValue = (EditText)findViewById(R.id.ed_value);

        cardNumber = (EditText)findViewById(R.id.ed_card_number);
        cardNumber.addTextChangedListener(new FourDigitCardFormatWatcher());

        cvv = (EditText)findViewById(R.id.ed_cvv);
        expiryDate = (MaskedEditText)findViewById(R.id.ed_expiry_date);

        layoutCard = (LinearLayout)findViewById(R.id.layout_card);
        layoutCard.setVisibility(View.INVISIBLE);

        layoutValue = (LinearLayout)findViewById(R.id.layout_value);

        layoutCardRegistered = (LinearLayout)findViewById(R.id.layout_card_registered);
        layoutCardRegistered.setVisibility(View.INVISIBLE);

        layoutSuccess = (LinearLayout)findViewById(R.id.layout_succes);
        layoutSuccess.setVisibility(View.INVISIBLE);

        btnValueOk = (Button)findViewById(R.id.btn_value_ok);
        btnFinishTransfer = (Button)findViewById(R.id.btn_card_finish);
        btnFinishTransferCardRegistered = (Button)findViewById(R.id.btn_finish_card_registered);
        btnRemoveCard = (Button)findViewById(R.id.btn_remove_card);
        btnSuccess = (Button)findViewById(R.id.btn_success);

        txtSuccesName = (TextView)findViewById(R.id.txt_name_success);
        txtSuccess = (TextView)findViewById(R.id.txt_success);

        imgSuccess = (ImageView)findViewById(R.id.img_success);

        card = new Card();
        cardDAO = new CardDAO(RegisterCard.this);

        btnValueOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOkValueAction();
            }
        });

        btnFinishTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cardDataIsOk()){
                    cardDAO.upgradeTable();
                    cardDAO.insert(card);
                    cardDAO.close();

                    AsyncPost task = new AsyncPost(transferData);
                    task.execute();
                }
            }
        });

        btnFinishTransferCardRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                transferData.setDestinationUserId(userId);

                if(cardDAO.getCardValue("num") != "null"){
                    transferData.setCardNumber(cardDAO.getCardValue("num"));
                }
                if(cardDAO.getCardValue("cvv") != "null"){
                    transferData.setCvv(cardDAO.getCardValue("cvv"));
                }
                if(cardDAO.getCardValue("date") != "null"){
                    transferData.setExpiryDate(cardDAO.getCardValue("date"));
                }

                AsyncPost task = new AsyncPost(transferData);
                task.execute();
            }
        });

        btnRemoveCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardDAO.upgradeTable();
                layoutCardRegToLayoutCardAnim();
                isLayoutCardRegistered = false;
            }
        });

        btnSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void btnOkValueAction(){
         String first = transferValue.getText().toString();

         if(first.isEmpty()){

             Toast.makeText(getApplicationContext(), "Insira um valor!", Toast.LENGTH_LONG).show();

         } else if(first.substring(0, 1).contentEquals("-")){

             Toast.makeText(getApplicationContext(), "Número negativo não é aceito!", Toast.LENGTH_LONG).show();

         } else if(first.substring(0, 1).contentEquals("+")) {

             Toast.makeText(getApplicationContext(), "Remova o sinal \"+\" no início do número!", Toast.LENGTH_LONG).show();

         } else {

             transferData.setValue(transferValue.getText().toString());

             final String cardNum = cardDAO.getRegisteredCard();
             Log.d("dbtest", "cardNum: "+ cardDAO.getRegisteredCard());

             if(cardNum == "null"){

                 layoutValueToLayoutCardAnim();
                 isLayoutCardRegistered = false;

             } else {
                 layoutValueToLayoutCardRegAnim(cardNum);
                 isLayoutCardRegistered = true;
             }
         }

     }

    private void layoutValueToLayoutCardRegAnim(final String cardNum){
         layoutValue.animate()
                 .translationX(-layoutValue.getWidth())
                 .alpha(0.0f)
                 .setListener(null);

         layoutCardRegistered.animate()
                 .translationX(0)
                 .alpha(1.0f)
                 .setListener(new AnimatorListenerAdapter() {
                     @Override
                     public void onAnimationEnd(Animator animation) {
                         super.onAnimationEnd(animation);
                         layoutCardRegistered.setVisibility(View.VISIBLE);
                         btnFinishTransferCardRegistered.setText(getResources().getString(R.string.finaliza_cartao_final )
                                 + cardNum.substring(12));
                         btnRemoveCard.setText(getResources().getString(R.string.remover_cartao)
                                 + cardNum.substring(12));
                     }
                 });
     }

    private void layoutValueToLayoutCardAnim(){
         layoutValue.animate()
                 .translationX(-layoutValue.getWidth())
                 .alpha(0.0f)
                 .setListener(null);

         layoutCard.animate()
                 .translationX(0)
                 .alpha(1.0f)
                 .setListener(new AnimatorListenerAdapter() {
                     @Override
                     public void onAnimationEnd(Animator animation) {
                         super.onAnimationEnd(animation);
                         layoutCard.setVisibility(View.VISIBLE);
                     }
                 });

     }

    private void layoutCardRegToLayoutCardAnim(){
        layoutCardRegistered.animate()
                .translationX(-layoutCardRegistered.getWidth())
                .alpha(0.0f)
                .setListener(null);

        layoutCard.animate()
                .translationX(0)
                .alpha(1.0f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        layoutCard.setVisibility(View.VISIBLE);
                    }
                });

    }

    private boolean cardDataIsOk(){
        String str = this.cardNumber.getText().toString();

        String date = this.expiryDate.getUnmaskedText();
        int year = Integer.parseInt(date.substring(4, 8));
        int month = Integer.parseInt(date.substring(2, 4));
        int day = Integer.parseInt(date.substring(0, 2));

        String actualDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        int actualYear = Integer.parseInt(actualDate.substring(6, 10));
        int actualMonth= Integer.parseInt(actualDate.substring(3, 5));
        int actualDay = Integer.parseInt(actualDate.substring(0, 2));

        if(this.cardNumber.getText().toString().isEmpty() || this.cardNumber.getText().toString() == null
                || this.cardNumber.getText().toString().length() < 19){

            Toast.makeText(this, "Informe o número do cartão corretamente!", Toast.LENGTH_LONG).show();
            return false;

        } else if(this.cvv.getText().toString().isEmpty() || this.cvv.getText().toString() == null
                || this.cvv.getText().toString().length() < 3){

            Toast.makeText(this, "Informe o número do cvv corretamente!", Toast.LENGTH_LONG).show();
            return false;

        } else if( day == 0 || month == 0 || year == 0) {

            Toast.makeText(this, "Nâo é permitido valor zerado \"00\" na data!", Toast.LENGTH_LONG).show();
            return false;

        } else if(date.isEmpty() || date == null || date.length() < 8 || day > 31 || month > 12){

            Toast.makeText(this, "Informe a data de vencimento corretamente!", Toast.LENGTH_LONG).show();
            return false;

        } else if(!validateDays(day, month)){

            Toast.makeText(this, "Informe o dia corretamente para esse mês!", Toast.LENGTH_LONG).show();
            return false;

        } else if(actualYear > year){

            Toast.makeText(this, "A data informada ja venceu!", Toast.LENGTH_LONG).show();
            return false;

        } else if(actualYear == year && actualMonth > month){

            Toast.makeText(this, "A data informada ja venceu!", Toast.LENGTH_LONG).show();
            return false;

        } else if(actualYear == year && actualMonth == month && actualDay > day){

            Toast.makeText(this, "A data informada ja venceu!", Toast.LENGTH_LONG).show();
            return false;

        } else if(!this.cardNumber.getText().toString().contentEquals("1111 1111 1111 1111")){

            Toast.makeText(this, "Cartão recusado.", Toast.LENGTH_SHORT).show();
            return false;

        } else {

            String aux = this.cardNumber.getText().toString().replaceAll("\\s","");
            Log.d("replaceaux", "aux = " + aux);
            String expiryDate = this.expiryDate.getText().toString();

            card.setCardNumber(aux);
            card.setCvv(Integer.parseInt(this.cvv.getText().toString()));
            card.setExpiryDate(expiryDate);

            transferData.setCardNumber(aux);
            transferData.setCvv(this.cvv.getText().toString());
            transferData.setExpiryDate(expiryDate);
            transferData.setDestinationUserId(userId);

            return true;
        }
    }

    private boolean validateDays(int day, int month){

        if( (month == 2) && day > 28 ){

            return false;

        }  else if( (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
                &&  day > 31 ){

            return false;

        } else if ( (month == 4 || month == 6 || month == 9 || month == 11) && day > 30 ){

            return false;

        }

        else {

         return true;

        }
    }

    public class AsyncPost extends AsyncTask<Void,Void,String[]> {

        private TransferData transferData;

        public AsyncPost(TransferData transferData){
            this.transferData = transferData;
        }

        @Override
        protected String[] doInBackground(Void... params) {
            String[] values = new String[4];
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

                    JSONObject jsonResponseObject = new JSONObject(jsonResponse);

                    Log.d(RegisterCard.class.getSimpleName(), "posttest: " + httpURLConnection.getResponseCode());
                    Log.d(RegisterCard.class.getSimpleName(), "posttest: " + inputStream);
                    Log.d(RegisterCard.class.getSimpleName(), "posttest: " + jsonResponse);

                    values[0] = jsonResponseObject.getJSONObject("transaction")
                            .getString("status");

                    values[1] = jsonResponseObject.getJSONObject("transaction")
                            .getString("value");

                    values[2] = jsonResponseObject.getJSONObject("transaction")
                            .getJSONObject("destination_user")
                            .getString("img");

                    values[3] = jsonResponseObject.getJSONObject("transaction")
                            .getJSONObject("destination_user")
                            .getString("name");

                    Log.d(RegisterCard.class.getSimpleName(), "posttest: " + values[0]);

                    Log.d(RegisterCard.class.getSimpleName(), "posttest: " + values[1]);

                    Log.d(RegisterCard.class.getSimpleName(), "posttest: " + values[2]);

                    Log.d(RegisterCard.class.getSimpleName(), "posttest: " + values[3]);

                } else {
                    Log.e(RegisterCard.class.getSimpleName(), "Error response code: " + httpURLConnection.getResponseCode());
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return values;
        }

        @Override
        protected void onPostExecute(String[] values) {
            if(values[0].contentEquals("Aprovada")){
                updateUi(values);
            } else {
                Toast.makeText(RegisterCard.this, "Transferência negada.", Toast.LENGTH_SHORT).show();
            }

        }

        private void updateUi(String[] values){
            txtSuccess.setText( getResources().getString(R.string.transferencia_sucesso)
                   + " " + values[1] + " " + getResources().getString(R.string.transferencia_sucesso_para));
            loadImageFromUrl(values[2]);
            txtSuccesName.setText(values[3]);

            if(isLayoutCardRegistered){

                layoutCardRegistered.animate()
                        .translationX(-layoutValue.getWidth())
                        .alpha(0.0f)
                        .setListener(null);
            } else {

                layoutCard.animate()
                        .translationX(-layoutValue.getWidth())
                        .alpha(0.0f)
                        .setListener(null);
            }

            layoutSuccess.animate()
                    .translationX(0)
                    .alpha(1.0f)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            layoutSuccess.setVisibility(View.VISIBLE);
                        }
                    });
        }

        private void loadImageFromUrl(String url) {
            Picasso.with(getApplicationContext()).load(url).placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(imgSuccess, new com.squareup.picasso.Callback(){

                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {

                        }
                    });
        }

    }
}