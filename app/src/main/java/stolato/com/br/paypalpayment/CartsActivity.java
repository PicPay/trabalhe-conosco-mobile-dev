package stolato.com.br.paypalpayment;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import stolato.com.br.paypalpayment.Adapter.CardAdapter;
import stolato.com.br.paypalpayment.DB.Banco;
import stolato.com.br.paypalpayment.Model.Card;

public class CartsActivity extends AppCompatActivity {
    private Banco db = new Banco();
    private ArrayList<Card> cards;
    private ListView listView;
    private FloatingActionButton add;
    private CardAdapter adapter;

    @Override
    protected void onRestart() {
        super.onRestart();
        updateList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carts);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Meus Cartões");
        listView = (ListView) findViewById(R.id.listCarts);
        db.startBanco(getApplication());
        cards = new ArrayList<>();
        adapter = new CardAdapter(this,R.layout.lista_card,cards);
        listView.setAdapter(adapter);
        add = (FloatingActionButton) findViewById(R.id.btAdd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(),AddCartActivity.class);
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplication(),EditCartActivity.class);
                intent.putExtra("id",cards.get(i).getId()+"");
                startActivity(intent);
            }
        });
        updateList();
    }

    public void updateList(){
        cards.clear();
        Cursor cursor = db.getCards();
        int IndiceID = db.Indice("id");
        int IndiceNome = db.Indice("nome");
        int IndiceNumber = db.Indice("number");
        int IndiceExpiry = db.Indice("expiry");
        while (!cursor.isAfterLast()){
            cards.add(new Card(cursor.getInt(IndiceID),cursor.getString(IndiceNome),cursor.getString(IndiceNumber),cursor.getString(IndiceExpiry)));
            cursor.moveToNext();
        }
        adapter.notifyDataSetChanged();
    }
}
