package gilianmarques.dev.picpay_test.asyncs;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import gilianmarques.dev.picpay_test.models.CreditCard;
import gilianmarques.dev.picpay_test.models.Transaction;

public class TransactionSenderAsynck extends AsyncTask<Void, Void, JSONObject> {

    private final TrasactionInterface mCallback;
    private final Transaction mTransaction;

    public TransactionSenderAsynck(TrasactionInterface mCallback, Transaction mTransaction) {
        this.mCallback = mCallback;
        this.mTransaction = mTransaction;
    }


    protected JSONObject doInBackground(Void... aVoid) {

        JSONObject mJsonObjectToUp = new JSONObject();
        JSONObject mJsonObjectToDown = new JSONObject();

        CreditCard mCard = mTransaction.getCard();

        try {

            mJsonObjectToUp.put("card_number", "1111111111111111"/*O numero que vai aprovar*/);
            mJsonObjectToUp.put("cvv", mCard.getCvv());
            mJsonObjectToUp.put("value", mTransaction.getAmount());
            mJsonObjectToUp.put("expiry_date", mCard.getExpireDate());
            mJsonObjectToUp.put("destination_user_id", mTransaction.getContact().getId());

            String URL = "http://careers.picpay.com/tests/mobdev/transaction";
            HttpURLConnection mHttpURLConnection = (HttpURLConnection) new URL(URL).openConnection();
            mHttpURLConnection.setDoOutput(true);
            mHttpURLConnection.setRequestMethod("POST");
            mHttpURLConnection.setUseCaches(false);
            mHttpURLConnection.setConnectTimeout(10000);
            mHttpURLConnection.setReadTimeout(10000);
            mHttpURLConnection.setRequestProperty("Content-Type", "application/json");
            mHttpURLConnection.connect();

            OutputStream mOutputStream = mHttpURLConnection.getOutputStream();
            mOutputStream.write(mJsonObjectToUp.toString().getBytes("UTF-8"));
            mOutputStream.close();

            StringBuilder mStringBuilder;

            if (mHttpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                mStringBuilder = new StringBuilder();
                InputStreamReader streamReader = new InputStreamReader(mHttpURLConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                String response;
                while ((response = bufferedReader.readLine()) != null) {
                    mStringBuilder.append(response).append("\n");
                }
                bufferedReader.close();
                mJsonObjectToDown = new JSONObject(mStringBuilder.toString());
            } else
                mCallback.fail(String.valueOf(mHttpURLConnection.getResponseCode()).concat(": ").concat(mHttpURLConnection.getResponseMessage()));


        } catch (IOException | JSONException e) {
            e.printStackTrace();
            mCallback.fail(e.getMessage());
        }

        return mJsonObjectToDown;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        if (jsonObject != null) mCallback.success(jsonObject);
        else mCallback.fail("null Json");
        super.onPostExecute(jsonObject);
    }

    public interface TrasactionInterface {
        void success(JSONObject response);
        void fail(String errorMessage);
    }


}
