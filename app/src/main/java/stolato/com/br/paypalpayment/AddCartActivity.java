package stolato.com.br.paypalpayment;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import stolato.com.br.paypalpayment.DB.Banco;
import stolato.com.br.paypalpayment.Util.Mask;

public class AddCartActivity extends AppCompatActivity {

    private Banco db;
    private EditText nome,expiry,numero;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cart);
        getSupportActionBar().setTitle("Adicionar novo Cartão");
        nome = (EditText) findViewById(R.id.nomeAdd);
        expiry = (EditText) findViewById(R.id.expiry);
        numero = (EditText) findViewById(R.id.numeroAdd);
        expiry.addTextChangedListener(Mask.insert("##/##", expiry));
        db = new Banco();
        db.startBanco(getApplicationContext());
        button = (Button) findViewById(R.id.btAddCard);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check()){
                    db.Insert(nome.getText().toString(),numero.getText().toString(),expiry.getText().toString());
                    alert();
                }
            }
        });
    }

    public void alert(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Aviso!");
        alert.setMessage("Cartão adicionado com sucesso.");
        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                finish();
            }
        });
        alert.create().show();
    }

    public Boolean check(){
        boolean t = true;
        int ct = numero.getText().length();
        if(numero.getText().toString().equals("") && ct < 16){
            numero.setError("Preencha o campos");
            return false;
        }
        else if(nome.getText().toString().equals("")){ t = false; nome.setError("Preencha o campo"); }
        else if(expiry.getText().toString().equals("")){ t = false; expiry.setError("Preencha o campo"); }
        return t;
    }
}
