package stolato.com.br.paypalpayment;

import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import stolato.com.br.paypalpayment.DB.Banco;
import stolato.com.br.paypalpayment.Model.Card;
import stolato.com.br.paypalpayment.Util.Mask;

public class EditCartActivity extends AppCompatActivity {

    private Banco db = new Banco();
    private int ID;
    private Cursor cursor;
    private EditText nome,numero,expiry;
    private Button add,remove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cart);
        getSupportActionBar().setTitle("Editar Cartão");
        nome = (EditText) findViewById(R.id.nomeEdit);
        numero = (EditText) findViewById(R.id.numeroEdit);
        numero.addTextChangedListener(Mask.insert("####.####.####.####",numero));
        expiry = (EditText) findViewById(R.id.expiryEdit);
        db.startBanco(getApplication());
        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("id")!= null)
        {
            ID = Integer.parseInt(bundle.getString("id"));
        }
        Log.i("CARD",ID+"");
        cursor = db.getCard(ID);
        int IndiceNome = db.Indice("nome");
        int IndiceNumero = db.Indice("number");
        int IndiceExpiry = db.Indice("expiry");
        nome.setText(cursor.getString(IndiceNome));
        numero.setText(cursor.getString(IndiceNumero));
        expiry.setText(cursor.getString(IndiceExpiry));
        expiry.addTextChangedListener(Mask.insert("##/##", expiry));
        remove = (Button) findViewById(R.id.btRemoveCard);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert();
            }
        });
        add = (Button) findViewById(R.id.btEditCard);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.Update(ID,nome.getText().toString(),numero.getText().toString(),expiry.getText().toString());
                Toast.makeText(getApplicationContext(),"Cartão atualizado com sucesso.",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public void alert(){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Aviso!!");
        alert.setMessage("Tem certeza que deseja excluir este cartão?");
        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db.Delete(ID);
                Toast.makeText(getApplicationContext(),"Cartão excluido com sucesso.",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.create().show();

    }
}
