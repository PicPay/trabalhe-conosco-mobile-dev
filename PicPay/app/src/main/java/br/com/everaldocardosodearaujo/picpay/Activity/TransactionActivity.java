package br.com.everaldocardosodearaujo.picpay.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import br.com.everaldocardosodearaujo.picpay.App.FunctionsApp;
import br.com.everaldocardosodearaujo.picpay.R;
import de.hdodenhof.circleimageview.CircleImageView;

import static br.com.everaldocardosodearaujo.picpay.App.FunctionsApp.closeActivity;
import static br.com.everaldocardosodearaujo.picpay.App.FunctionsApp.formatMask;
import static br.com.everaldocardosodearaujo.picpay.App.FunctionsApp.startActivity;
import static br.com.everaldocardosodearaujo.picpay.App.SessionApp.MASK_NUMBER;
import static br.com.everaldocardosodearaujo.picpay.App.SessionApp.TB_CREDIT_CARD;

public class TransactionActivity extends Activity {

    private CircleImageView idImg;
    private TextView idUserName;
    private TextView idName;
    private Button  idBtnPay;
    private TextView idTxvCardNumber;
    private EditText idValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        this.inflate();
        this.loadParams();
    }

    private void inflate(){
        this.idImg = (CircleImageView) findViewById(R.id.idImg);
        this.idName = (TextView) findViewById(R.id.idName);
        this.idUserName = (TextView) findViewById(R.id.idUserName);
        this.idBtnPay = (Button) findViewById(R.id.idBtnPay);
        this.idTxvCardNumber = (TextView) findViewById(R.id.idTxvCardNumber);
        this.idValue = (EditText)  findViewById(R.id.idValue);
    }

    private void loadParams(){
        try{
            Intent intent = this.getIntent();
            this.idName.setText(intent.getExtras().getString("name"));
            this.idUserName.setText(intent.getExtras().getString("username"));
            if (!intent.getExtras().getString("img").equals("")){
                Picasso.with(TransactionActivity.this).load(intent.getExtras().getString("img")).into(this.idImg);
            }
            String numberCard = TB_CREDIT_CARD.select().get(0).getNumberCard();
            this.idTxvCardNumber.setText("Cartão de crédito final " + numberCard.substring(15,numberCard.length()));
        }catch (Exception ex){
            Toast.makeText(TransactionActivity.this,"Ocorreu um erro: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickPay(View view){
        try{
            Toast.makeText(TransactionActivity.this,"Em desenvolvimento", Toast.LENGTH_SHORT).show();
        }catch (Exception ex){
            Toast.makeText(TransactionActivity.this,"Ocorreu um erro: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickShowCreditCard(View view){
        FunctionsApp.startActivity(TransactionActivity.this,CreditCardActivity.class,null);
    }
    @Override
    public void onResume(){
        super.onResume();
        try{
            String numberCard = TB_CREDIT_CARD.select().get(0).getNumberCard();
            this.idTxvCardNumber.setText("Cartão de crédito final " + numberCard.substring(15,numberCard.length()));
        }catch (Exception ex){
            Toast.makeText(TransactionActivity.this,"Ocorreu um erro: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void onClickReturn(View view){
        closeActivity(TransactionActivity.this);
    }
}
