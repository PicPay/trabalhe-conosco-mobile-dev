package br.com.devmarques.picpaytestes.main;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import br.com.devmarques.picpaytestes.Fragmentos.Pessoas.FragmentoListaPessoas;
import br.com.devmarques.picpaytestes.Fragmentos.Transacao.FragListaHitorico;
import br.com.devmarques.picpaytestes.Fragmentos.Pessoas.Perfil;
import br.com.devmarques.picpaytestes.Fragmentos.Cartao.ListaCardCadastrados;
import br.com.devmarques.turismo.picpaytestes.R;


public class Main2Activity extends AppCompatActivity {
    Bundle args = new Bundle();
    FragmentoListaPessoas Frag_lista;
    boolean listafechar =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listadepessoas);

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        final ActionBar bar = getSupportActionBar();
        bar.hide();



        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {


                if (tabId == R.id.perfil) {

                    if (listafechar){
                        android.support.v4.app.FragmentTransaction fragmentTransaction =
                                getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.remove(Frag_lista);
                        fragmentTransaction.commit();
                        listafechar= false;
                    }

                    Perfil perfil = new Perfil();
                    android.support.v4.app.FragmentTransaction fragmentTransaction =
                            getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.conteinerf, perfil);
                    fragmentTransaction.commit();

                }

                if (tabId == R.id.list) {

                    listafechar = true;

                    Frag_lista = new FragmentoListaPessoas();
                    android.support.v4.app.FragmentTransaction fragmentTransaction =
                            getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.conteinerf, Frag_lista);
                    fragmentTransaction.commit();

                }

                if (tabId == R.id.transacoes) {

                    if (listafechar){
                        android.support.v4.app.FragmentTransaction fragmentTransaction =
                                getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.remove(Frag_lista);
                        fragmentTransaction.commit();
                        listafechar= false;
                    }

                    FragListaHitorico hitorico = new FragListaHitorico();
                    android.support.v4.app.FragmentTransaction fragmentTransaction =
                            getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.conteinerf, hitorico);
                    fragmentTransaction.commit();

                }

                if (tabId == R.id.cartoes) {

                    if (listafechar){
                        android.support.v4.app.FragmentTransaction fragmentTransaction =
                                getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.remove(Frag_lista);
                        fragmentTransaction.commit();
                        listafechar= false;
                    }

                    ListaCardCadastrados ListaCard = new ListaCardCadastrados();
                    android.support.v4.app.FragmentTransaction fragmentTransaction =
                            getSupportFragmentManager().beginTransaction();

                    args.putBoolean("selecionecard", false);
                    ListaCard.setArguments(args);

                    fragmentTransaction.replace(R.id.conteinerf, ListaCard);
                    fragmentTransaction.commit();

                }

            }
        });


    }
}
