package br.com.devmarques.picpaytestes.Fragmentos.Cartao;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.devmarques.picpaytestes.Adapter.AdapterCard;
import br.com.devmarques.picpaytestes.DAO.CartoesCad.CartaoDAO;
import br.com.devmarques.picpaytestes.Dados.Cartao;
import br.com.devmarques.turismo.picpaytestes.R;

/**
 * Created by Roger on 12/11/2017.
 */

public class SelecioneOCartao extends Fragment {

    View root;
    ArrayList<Cartao> cartao = new ArrayList<Cartao>();
    CartaoDAO cartaoDAO;
    ImageView adicionar, adicionarprimeiro;

    String idUSER;
    String usuarioDestinatarioNome, img,username ;

    Bundle args = new Bundle();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.cartao, container, false);
        InstanciandoBanco();

        // recebendo dados
        idUSER = getArguments().getString("userID");
        usuarioDestinatarioNome = getArguments().getString("Nome");
        img = getArguments().getString("Img");
        username =  getArguments().getString("Usuario");

        Toast.makeText(getContext(), usuarioDestinatarioNome , Toast.LENGTH_SHORT).show();

        cartao = cartaoDAO.buscaContatos();

        if (cartao.size() == 0){
            root = inflater.inflate(R.layout.adicionarcardvazio, container, false);
            adicionarprimeiro = root.findViewById(R.id.vazioaddnovo);
            adicionarprimeiro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CadastrarCartao cadastrarCartao = new CadastrarCartao();
                    android.support.v4.app.FragmentTransaction fragmentTransaction =
                            getFragmentManager().beginTransaction();

                    // setando true para lista de pessoas > entrar no salvamento do primeiro cartão
                    args.putBoolean("selecionecard",true);

                    // pegando dados
                    args.putString("userID", idUSER);
                    args.putString("Nome", usuarioDestinatarioNome);
                    args.putString("Usuario", username);
                    args.putString("Img", img);

                    cadastrarCartao.setArguments(args);

                    fragmentTransaction.replace(R.id.conteinerf, cadastrarCartao);
                    fragmentTransaction.commit();
                }
            });

        }else {
            ListaRecycler();
        }

        return root;
    }

    public void InstanciandoBanco(){
        cartaoDAO = new CartaoDAO(getContext());
    }

    public void ListaRecycler(){
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL); // (int spanCount, int orientation)
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.listarcartoes);
        recyclerView.setAdapter(new AdapterCard( cartao  ,getContext(), true, idUSER, usuarioDestinatarioNome,  img, username  ));
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
    }

}
