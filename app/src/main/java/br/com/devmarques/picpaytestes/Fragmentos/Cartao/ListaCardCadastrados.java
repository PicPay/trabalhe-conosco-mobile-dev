package br.com.devmarques.picpaytestes.Fragmentos.Cartao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.devmarques.picpaytestes.Adapter.AdapterCard;
import br.com.devmarques.picpaytestes.DAO.CartoesCad.CartaoDAO;
import br.com.devmarques.picpaytestes.Dados.Cartao;
import br.com.devmarques.turismo.picpaytestes.R;

/**
 * Created by Roger on 09/11/2017.
 */

public class ListaCardCadastrados extends Fragment {

    View root;
    ArrayList<Cartao> cartao = new ArrayList<Cartao>();
    CartaoDAO cartaoDAO;
    ImageView adicionar, adicionarprimeiro;
    Bundle args = new Bundle();
    Boolean b=false;
    String idUSER;
    String usuarioDestinatarioNome, img,username ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.cartao, container, false);
        InstanciandoBanco();
        findByid();
        cartao = cartaoDAO.buscaContatos();
        b = getArguments().getBoolean("selecionecard");

        if (b){
            idUSER = getArguments().getString("userID");
            usuarioDestinatarioNome = getArguments().getString("Nome");
            img = getArguments().getString("Img");
            username =  getArguments().getString("Usuario");
        }

        if (cartao.size() == 0){
            root = inflater.inflate(R.layout.adicionarcardvazio, container, false);
            adicionarprimeiro = root.findViewById(R.id.vazioaddnovo);
            adicionarprimeiro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CadastrarCartao cadastrarCartao = new CadastrarCartao();
                    android.support.v4.app.FragmentTransaction fragmentTransaction =
                            getFragmentManager().beginTransaction();
                    args.putBoolean("selecionecard", b);

                    args.putString("userID",idUSER);
                    args.putString("Nome",usuarioDestinatarioNome);
                    args.putString("Usuario", img);
                    args.putString("Img", username);

                    cadastrarCartao.setArguments(args);

                    fragmentTransaction.replace(R.id.conteinerf, cadastrarCartao);
                    fragmentTransaction.commit();
                }
            });

        }else {

            adicionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //adicionar novo cartão
                    CadastrarCartao cadastrarCartao = new CadastrarCartao();
                    android.support.v4.app.FragmentTransaction fragmentTransaction =
                            getFragmentManager().beginTransaction();
                    args.putBoolean("selecionecard", b);
                    cadastrarCartao.setArguments(args);
                    fragmentTransaction.replace(R.id.conteinerf, cadastrarCartao);
                    fragmentTransaction.commit();
                }
            });

            ListaRecycler();
        }

        return root;
    }

    public void findByid(){
        adicionar = root.findViewById(R.id.addCard);

    }

    public void InstanciandoBanco(){
        cartaoDAO = new CartaoDAO(getContext());
    }

    public void ListaRecycler(){
        if (b){
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL); // (int spanCount, int orientation)
            RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.listarcartoes);
            recyclerView.setAdapter(new AdapterCard( cartao  ,getContext(),b ,idUSER, usuarioDestinatarioNome,  img, username   ));
            recyclerView.setLayoutManager(staggeredGridLayoutManager);
        }else {
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL); // (int spanCount, int orientation)
            RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.listarcartoes);
            recyclerView.setAdapter(new AdapterCard(cartao, getContext(), b));
            recyclerView.setLayoutManager(staggeredGridLayoutManager);
        }
    }

    public void removeFragment(Fragment fragment){
        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }

}
