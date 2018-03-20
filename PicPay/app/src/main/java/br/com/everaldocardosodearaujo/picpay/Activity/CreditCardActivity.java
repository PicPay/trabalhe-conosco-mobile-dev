package br.com.everaldocardosodearaujo.picpay.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import br.com.everaldocardosodearaujo.picpay.App.FunctionsApp;
import br.com.everaldocardosodearaujo.picpay.Object.CreditCardObject;
import br.com.everaldocardosodearaujo.picpay.R;
import br.com.everaldocardosodearaujo.picpay.Repository.CreditCardRepository;

import static br.com.everaldocardosodearaujo.picpay.App.FunctionsApp.closeActivity;
import static br.com.everaldocardosodearaujo.picpay.App.FunctionsApp.formatMask;
import static br.com.everaldocardosodearaujo.picpay.App.FunctionsApp.modal;
import static br.com.everaldocardosodearaujo.picpay.App.SessionApp.MASK_CCV;
import static br.com.everaldocardosodearaujo.picpay.App.SessionApp.MASK_DATE_MONTH_YEAR;
import static br.com.everaldocardosodearaujo.picpay.App.SessionApp.MASK_NUMBER_CREDIT_CARD;
import static br.com.everaldocardosodearaujo.picpay.App.SessionApp.TB_CREDIT_CARD;

public class CreditCardActivity extends Activity {

    private Spinner idSpFlag;
    private EditText idEdtName;
    private EditText idEdtNumberCard;
    private EditText idEdtValitity;
    private EditText idEdtCCV;
    private Button idBtnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);
        FunctionsApp.modal(CreditCardActivity.this,"Atenção","Será necessário cadastrar um cartão de crédito","OK");
        this.inflate();
        this.loadFlags();
    }

    private void inflate(){
        this.idSpFlag = (Spinner) findViewById(R.id.idSpFlag);
        this.idEdtName = (EditText)findViewById(R.id.idEdtName);
        this.idEdtNumberCard = (EditText) findViewById(R.id.idEdtNumberCard);
        this.idEdtValitity = (EditText) findViewById(R.id.idEdtValitity);
        this.idEdtCCV = (EditText) findViewById(R.id.idEdtCCV);
        this.idBtnSave = (Button) findViewById(R.id.idBtnSave);

        formatMask(this.idEdtNumberCard,MASK_NUMBER_CREDIT_CARD);
        formatMask(this.idEdtValitity,MASK_DATE_MONTH_YEAR);
        formatMask(this.idEdtCCV,MASK_CCV);

        this.idSpFlag.requestFocus();
    }

    private void loadFlags(){
        String[] flags = new String[] {"Mastercard", "Visa", "American Express", "Elo", "Hipercard"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, flags);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.idSpFlag.setAdapter(adapter);
    }

    public void onClickSalvar(View view){
        try{
            if (this.idSpFlag.getSelectedItem().toString().trim().equals("")){
                modal(CreditCardActivity.this,"Atenção!","Informe a bandeira!","OK");
                this.idSpFlag.requestFocus();
                return;
            }

            if (this.idEdtName.getText().toString().trim().equals("")){
                modal(CreditCardActivity.this,"Atenção!","Informe o nome escrito no cartão!","OK");
                this.idEdtName.requestFocus();
                return;
            }

            if (this.idEdtNumberCard.getText().toString().trim().equals("")){
                modal(CreditCardActivity.this,"Atenção!","Informe o número do cartão!","OK");
                this.idEdtNumberCard.requestFocus();
                return;
            }

            if (this.idEdtValitity.getText().toString().trim().equals("")){
                modal(CreditCardActivity.this,"Atenção!","Informe a data de validade!","OK");
                this.idEdtValitity.requestFocus();
                return;
            }

            if (this.idEdtCCV.getText().toString().trim().equals("")){
                modal(CreditCardActivity.this,"Atenção!","Informe o código CCV que fica atrás do cartão!","OK");
                this.idEdtCCV.requestFocus();
                return;
            }

            CreditCardObject creditCardObject = new CreditCardObject();
            creditCardObject.setFlag(this.idSpFlag.getSelectedItem().toString());
            creditCardObject.setName(this.idEdtName.getText().toString());
            creditCardObject.setNumberCard(this.idEdtNumberCard.getText().toString());
            creditCardObject.setValidity(this.idEdtValitity.getText().toString());
            creditCardObject.setCcv(this.idEdtCCV.getText().toString());

            TB_CREDIT_CARD.insert(creditCardObject);

            closeActivity(CreditCardActivity.this);
            Toast.makeText(CreditCardActivity.this,"Cartão salvo com sucesso.", Toast.LENGTH_SHORT).show();
        }catch (Exception ex){
            modal(CreditCardActivity.this,"Atenção","Ocorreu um erro:" + ex.getMessage(),"OK");
        }
    }
}
