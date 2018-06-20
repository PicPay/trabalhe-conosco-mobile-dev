package br.com.gsas.app.picpay.Home;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import br.com.gsas.app.picpay.Carteira.CarteiraFragment;
import br.com.gsas.app.picpay.Contatos.ContatoFragment;
import br.com.gsas.app.picpay.Feed.FeedFragment;
import br.com.gsas.app.picpay.R;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton pagar;
    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpLayout();

        pagar.setOnClickListener(funcPagar());
        navigationView.setOnNavigationItemSelectedListener(funcSelect());

        FeedFragment feed = new FeedFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, feed, "Feed").commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener funcSelect() {

        return new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.menu_card:
                        pagar.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent1)));
                        CarteiraFragment frag = new CarteiraFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, frag, "Carteira").commit();

                        break;

                    case R.id.menu_home:

                        pagar.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent1)));
                        FeedFragment feed = new FeedFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, feed, "Feed").commit();

                        break;
                }

                return true;
            }
        };
    }

    private View.OnClickListener funcPagar() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                navigationView.setSelectedItemId(R.id.menu_pagar);

                pagar.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.verde_escuro)));


                ContatoFragment frag = new ContatoFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, frag, "Contato").commit();


            }
        };
    }

    private void  setUpLayout(){

        pagar = findViewById(R.id.fab_pagar);
        navigationView = findViewById(R.id.home_navi);

    }


}
