package br.com.everaldocardosodearaujo.picpay.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.everaldocardosodearaujo.picpay.API.API;
import br.com.everaldocardosodearaujo.picpay.App.FunctionsApp;
import br.com.everaldocardosodearaujo.picpay.Object.CreditCardObject;
import br.com.everaldocardosodearaujo.picpay.Object.TransactionObject;
import br.com.everaldocardosodearaujo.picpay.R;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.everaldocardosodearaujo.picpay.App.FunctionsApp.closeActivity;
import static br.com.everaldocardosodearaujo.picpay.App.FunctionsApp.closePgDialog;
import static br.com.everaldocardosodearaujo.picpay.App.FunctionsApp.modal;
import static br.com.everaldocardosodearaujo.picpay.App.FunctionsApp.showPgDialog;
import static br.com.everaldocardosodearaujo.picpay.App.SessionApp.TB_CREDIT_CARD;

public class TransactionActivity extends Activity {

    private CircleImageView idImg;
    private TextView idUserName;
    private TextView idName;
    private Button  idBtnPay;
    private TextView idTxvCardNumber;
    private EditText idEdtValue;
    private long id;

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
        this.idEdtValue = (EditText)  findViewById(R.id.idValue);
    }

    private void loadParams(){
        try{
            Intent intent = this.getIntent();
            this.id = intent.getExtras().getLong("destination_user_id");
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

            if (this.idEdtValue.getText().toString().equals("")){
                modal(TransactionActivity.this,"Atenção!","Informe o valor do pagamento!","OK");
                this.idEdtValue.requestFocus();
                return;
            }

            try{
                if (Double.parseDouble(this.idEdtValue.getText().toString())<=0){
                    modal(TransactionActivity.this,"Atenção!","Informe o valor do pagamento!","OK");
                    this.idEdtValue.requestFocus();
                    return;
                }
            }catch (Exception ex){
                modal(TransactionActivity.this,"Atenção!","Informe o valor do pagamento!","OK");
                this.idEdtValue.requestFocus();
                return;
            }
            showPgDialog(TransactionActivity.this);
            List<CreditCardObject> lstCreditCardObject = TB_CREDIT_CARD.select();
            if (lstCreditCardObject.size()>0){
                CreditCardObject creditCardObject = lstCreditCardObject.get(0);
                TransactionObject transactionObject = new TransactionObject();
                transactionObject.setCard_number("1111111111111111");
                transactionObject.setCcv(creditCardObject.getCcv());
                transactionObject.setValue(Double.parseDouble(this.idEdtValue.getText().toString()));
                transactionObject.setExpiry_date(creditCardObject.getValidity());
                transactionObject.setDestination_user_id(this.id);
                this.setTransactionFromApi(transactionObject);
            }
        }catch (Exception ex){
            closePgDialog();
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

    private void setTransactionFromApi(TransactionObject transactionObject){
        API.setTransaction(transactionObject)
                .enqueue(new Callback<TransactionObject>() {
                    @Override
                    public void onResponse(Call<TransactionObject> call, Response<TransactionObject> response) {
                        closePgDialog();
                        if (response.isSuccessful()) {
                            Toast.makeText(TransactionActivity.this,"Pagamento efetuado com sucesso!",Toast.LENGTH_LONG).show();
                            closeActivity(TransactionActivity.this);
                        } else {
                            modal(TransactionActivity.this,"Atenção!",response.message(),"OK");
                        }
                    }

                    @Override
                    public void onFailure(Call<TransactionObject> call, Throwable t) {
                        closePgDialog();
                        modal(TransactionActivity.this,"Atenção!","Ocorreu um erro: " + t.getMessage() + ". Causa: " + t.getCause(),"OK");
                    }
                });
    }
}
