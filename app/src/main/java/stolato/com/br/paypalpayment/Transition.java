package stolato.com.br.paypalpayment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import stolato.com.br.paypalpayment.API.Data;
import stolato.com.br.paypalpayment.DB.Banco;
import stolato.com.br.paypalpayment.Model.Card;
import stolato.com.br.paypalpayment.Model.Cliente;
import stolato.com.br.paypalpayment.Util.DelayedProgressDialog;

public class Transition extends AppCompatActivity {

    private ImageView avatar;
    private EditText cvv, valor;
    private Cliente cliente;
    private TextView nome,user;
    private Spinner card;
    private ProgressDialog progressDialog;
    private FloatingActionButton floatingActionButton;
    private Banco db = new Banco();
    private Cursor cursor;
    private ArrayList<Integer> ids;
    private ArrayList<Card> cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView logo_wgas = new ImageView(getApplicationContext());
        logo_wgas.setImageResource(R.drawable.logowhite);
        getSupportActionBar().setCustomView(logo_wgas,new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        cvv = (EditText) findViewById(R.id.cvv);
        valor = (EditText) findViewById(R.id.valor);
        avatar = (ImageView) findViewById(R.id.avatar);
        Bundle bundle = getIntent().getExtras();
        cliente = (Cliente) getIntent().getSerializableExtra("cliente");
        Picasso.get().load(cliente.getImg()).into(avatar);
        nome = (TextView) findViewById(R.id.ClienteName);
        user = (TextView) findViewById(R.id.ClienteUser);
        card = (Spinner) findViewById(R.id.cart);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check()) {
                    alertConf("Fazer a transferencia agora?", "Aviso!");
                }
            }
        });
        nome.setText(cliente.getName());
        user.setText(cliente.getUsername());

        ArrayList<String> list = new ArrayList<>();
        ids = new ArrayList<>();
        cards = new ArrayList<>();
        db.startBanco(getApplication());
        cursor = db.getCards();
        int IndiceID = db.Indice("id");
        int IndiceNome = db.Indice("nome");
        int IndiceNumber = db.Indice("number");
        int IndiceExpiry = db.Indice("expiry");
        while (!cursor.isAfterLast()) {
            ids.add(cursor.getInt(IndiceID));
            cards.add(new Card(cursor.getInt(IndiceID),cursor.getString(IndiceNome),cursor.getString(IndiceNumber),cursor.getString(IndiceExpiry)));
            String numbr = cursor.getString(IndiceNumber);
            list.add(cursor.getString(IndiceNome)+" - ( XXXX.XXXX.XXXX."+numbr.substring(15,19)+" )");
            cursor.moveToNext();
        }
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,list);
        card.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void send() throws JSONException {
        final DelayedProgressDialog delayedProgressDialog = new DelayedProgressDialog();
        delayedProgressDialog.show(getSupportFragmentManager(),"tag");
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(MainActivity.URL)
                .build();
        Data data = retrofit.create(Data.class);
        Card c = cards.get(card.getSelectedItemPosition());
        JSONObject jsonObject =  new JSONObject();
        jsonObject.put("card_number",c.getNumber().replace(".",""));
        jsonObject.put("cvv",cvv.getText().toString());
        jsonObject.put("value",valor.getText().toString());
        jsonObject.put("expiry_date",c.getExpiry());
        jsonObject.put("destination_user_id",cliente.getId());
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jsonObject).toString());
        Call<ResponseBody> call = data.Send(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if(response.body() != null) {
                        String t = response.body().string();
                        JSONObject object = new JSONObject(t);
                        boolean st = Boolean.parseBoolean(object.getJSONObject("transaction").get("success").toString());
                        if(st){
                            alert("Sua transferência para " + cliente.getName() + " foi aprovada.", "Aprovado", true);
                        }else{
                            alert("Sua transferência para " + cliente.getName() + " foi recusada.", "Recusado", false);
                        }
                        Log.i("Resposta", t);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                delayedProgressDialog.cancel();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("Resposta", t.getMessage());
                delayedProgressDialog.cancel();
            }
        });

    }

    public boolean check(){
        boolean t = true;
        if(valor.getText().toString().equals("")){valor.setError("Preencha o campos"); return false;}
        else if(cvv.getText().toString().equals("")){cvv.setError("Preencha o campos"); return false;}
        return true;
    }

    public void alert(String message, String title, final boolean fechar){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(message);
        dialog.setTitle(title);
        dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if(fechar){
                    finish();
                }
            }
        });
        dialog.create();
        dialog.show();
    }

    public void alertConf(String message, String title){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(message);
        dialog.setTitle(title);
        dialog.setPositiveButton("Tranferir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    send();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.create();
        dialog.show();
    }
}
