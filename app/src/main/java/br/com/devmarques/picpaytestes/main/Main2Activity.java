package br.com.devmarques.picpaytestes.main;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.prefs.AbstractPreferences;

import br.com.devmarques.picpaytestes.Fragmentos.Pessoas.FragmentoListaPessoas;
import br.com.devmarques.picpaytestes.Fragmentos.Transacao.FragListaHitorico;
import br.com.devmarques.picpaytestes.Fragmentos.Transacao.FragPerfilTransacao;
import br.com.devmarques.picpaytestes.Fragmentos.Cartao.ListaCardCadastrados;
import br.com.devmarques.turismo.picpaytestes.R;


public class Main2Activity extends AppCompatActivity {
    Bundle args = new Bundle();
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
                    //mudar texto ao mudar de opção
                    // textView.setText("Cadastrar Incidente");

                    //mudar de cor do fundo da barra no android api 21 acima.
                    if (Build.VERSION.SDK_INT >= 21) {
                        getWindow().setNavigationBarColor(getResources().getColor(R.color.verdeescuro));
                        getWindow().setStatusBarColor(getResources().getColor(R.color.verdeescuro));
                    }
                    // barra
                    //relativeLayout.setBackgroundResource(R.color.action3);
                    // bar.setBackgroundDrawable(getResources().getDrawable(R.color.action1));
                    FragPerfilTransacao perfil = new FragPerfilTransacao();
                    android.support.v4.app.FragmentTransaction fragmentTransaction =
                            getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.conteinerf, perfil);
                    fragmentTransaction.commit();

                }

                if (tabId == R.id.list) {
                    //mudar texto ao mudar de opção
                    //textView.setText("Pesquisar");
                    //mudar de cor do fundo da barra no android api 21 acima.
                    if (Build.VERSION.SDK_INT >= 21) {
                        //getWindow().setNavigationBarColor(getResources().getColor(R.color.action2));
                        // getWindow().setStatusBarColor(getResources().getColor(R.color.action2));
                    }
                    // barra
                    // relativeLayout.setBackgroundResource(R.color.action2);
                    /* bar.setBackgroundDrawable(getResources().getDrawable(R.color.action2));*/
                    FragmentoListaPessoas Frag_lista = new FragmentoListaPessoas();
                    android.support.v4.app.FragmentTransaction fragmentTransaction =
                            getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.conteinerf, Frag_lista);
                    fragmentTransaction.commit();



                }

                if (tabId == R.id.transacoes) {
                    //mudar texto ao mudar de opção
                    //textView.setText("Lista de Incidentes");
                    //mudar de cor do fundo da barra no android api 21 acima.
                    if (Build.VERSION.SDK_INT >= 21) {
                        // getWindow().setNavigationBarColor(getResources().getColor(action1));
                        // getWindow().setStatusBarColor(getResources().getColor(action1));
                    }
                    // barra
                    //relativeLayout.setBackgroundResource(R.color.action1);
                    //  bar.setBackgroundDrawable(getResources().getDrawable(R.color.action2));
                    FragListaHitorico hitorico = new FragListaHitorico();
                    android.support.v4.app.FragmentTransaction fragmentTransaction =
                            getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.conteinerf, hitorico);
                    fragmentTransaction.commit();

                }

                if (tabId == R.id.cartoes) {
                    //mudar texto ao mudar de opção
                    //textView.setText("Lista de Incidentes");
                    //mudar de cor do fundo da barra no android api 21 acima.
                    if (Build.VERSION.SDK_INT >= 21) {
                        // getWindow().setNavigationBarColor(getResources().getColor(action1));
                        // getWindow().setStatusBarColor(getResources().getColor(action1));
                    }
                    // barra
                    //relativeLayout.setBackgroundResource(R.color.action1);
                    //  bar.setBackgroundDrawable(getResources().getDrawable(R.color.action2));


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
