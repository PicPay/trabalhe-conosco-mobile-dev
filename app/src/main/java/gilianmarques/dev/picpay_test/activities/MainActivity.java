package gilianmarques.dev.picpay_test.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import gilianmarques.dev.picpay_test.R;
import gilianmarques.dev.picpay_test.crud.Database;
import gilianmarques.dev.picpay_test.utils.AppPatterns;
import gilianmarques.dev.picpay_test.utils.DialogSelectCreditCard;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String TAG = "cript";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_send_cash).setOnClickListener(this);
//=============================================//=============================================
//https://www.developer.com/ws/android/encrypting-with-android-cryptography-api.html
        // Original text
        String theTestText = "teste de criptografia";




        // Set up secret key spec for 128-bit AES encryption and decryption
        SecretKeySpec sks = null;
        try {
            SecureRandom mSecureRandom = SecureRandom.getInstance("SHA1PRNG");
            mSecureRandom.setSeed("any data used as random seed".getBytes());
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(256, mSecureRandom);
            sks = new SecretKeySpec((kg.generateKey()).getEncoded(), "AES");
        } catch (Exception e) {
            Log.e(TAG, "AES secret key spec error");
        }



        // Encode the original data with AES
        byte[] encodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, sks);
            encodedBytes = c.doFinal(theTestText.getBytes());
        } catch (Exception e) {
            Log.e(TAG, "AES encryption error");
        }
        Log.d(TAG, "onCreate: " + "[ENCODED]: " + Base64.encodeToString(encodedBytes, Base64.DEFAULT) +"\n");





        // Decode the encoded data with AES
        byte[] decodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE, sks);
            decodedBytes = c.doFinal(encodedBytes);
        } catch (Exception e) {
            Log.e(TAG, "AES decryption error");
        }
        Log.d(TAG, "onCreate: " + "[DECODED]: " + new String(decodedBytes) + "\n");
//=============================================//=============================================
    }

    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btn_send_cash) {
            startActivity(new Intent(this, SendCashActivity.class));
        }


    }
}
