package br.com.devmarques.picpaytestes.Fragmentos.Cartao;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.devmarques.picpaytestes.DAO.CartoesCad.CartaoDAO;
import br.com.devmarques.picpaytestes.Dados.Cartao;
import br.com.devmarques.turismo.picpaytestes.R;

/**
 * Created by Roger on 11/11/2017.
 */

public class CadastrarCartao extends Fragment {

    View root;
    Button salvar;
    ArrayList<Cartao> cartao = new ArrayList<Cartao>();
    CartaoDAO cartaoDAO;
    Cartao cartaoOBJ;
    EditText numeroCard, cvv;
    Spinner Bandeira, mes , ano;
    boolean selecionecard;
    String numero ="", cvvv="",bandeira="",mess="", anoo="";

    String idUSER;
    String usuarioDestinatarioNome, img,username ;
    boolean b=false;
    Bundle args = new Bundle();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.adicionarcartao, container, false);
        Find();
        InstanciandoBanco();
        cartao = cartaoDAO.buscaContatos();
        b = getArguments().getBoolean("selecionecard");
        if (b){
            idUSER = getArguments().getString("userID");
            usuarioDestinatarioNome = getArguments().getString("Nome");
            img = getArguments().getString("Img");
            username =  getArguments().getString("Usuario");
        }

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Bandeira));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Bandeira.setAdapter(myAdapter);

        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.arryMes));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mes.setAdapter(myAdapter2);

        ArrayAdapter<String> myAdapter3 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.arryAno));
        myAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ano.setAdapter(myAdapter3);

        Bandeira.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){bandeira="Visa";}
                if (i==1){bandeira="MasterCard";}
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){mess="01";}
                if (i==1){mess="02";}
                if (i==2){mess="03";}
                if (i==3){mess="04";}
                if (i==4){mess="05";}
                if (i==5){mess="06";}
                if (i==6){mess="07";}
                if (i==7){mess="08";}
                if (i==8){mess="09";}
                if (i==9){mess="10";}
                if (i==10){mess="11";}
                if (i==11){mess="12";}
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ano.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){anoo="2018";}
                if (i==1){anoo="2019";}
                if (i==2){anoo="2020";}
                if (i==3){anoo="2021";}
                if (i==4){anoo="2022";}
                if (i==5){anoo="2023";}
                if (i==6){anoo="2024";}
                if (i==7){anoo="2025";}
                if (i==8){anoo="2026";}
                if (i==9){anoo="2027";}
                if (i==10){anoo="2028";}
                if (i==11){anoo="2029";}
                if (i==12){anoo="2030";}
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numero = numeroCard.getText().toString();
                cvvv = cvv.getText().toString();

                if ((numero.length()==16)&&(cvvv.length()==3)){
                    AdicionarDados();
                }else {
                    Toast.makeText(getContext(), "Por favor preencha os dados corretamente."+numero.length(), Toast.LENGTH_LONG).show();
                }
            }
        });



        return root;
    }
    public void Find(){

        salvar = root.findViewById(R.id.salvarcartao);
        numeroCard = root.findViewById(R.id.numerocartao);
        cvv = root.findViewById(R.id.cvvcartao);
        Bandeira = root.findViewById(R.id.BandeiraNome);
        mes = root.findViewById(R.id.mes);
        ano = root.findViewById(R.id.ano);

    }

    public void AdicionarDados(){
        cartaoOBJ = new Cartao(bandeira,numero, cvvv, (mess+"/"+anoo));
        InstanciandoBanco();
        inserir(cartaoOBJ);
    }

    public void InstanciandoBanco(){
        cartaoDAO = new CartaoDAO(getContext());
    }

    private void inserir(Cartao cartao) {
        if (cartaoDAO.insere(cartao) > 0){
            Toast.makeText(getContext(), "Salvo com Sucesso!", Toast.LENGTH_SHORT).show();
                ListaCardCadastrados listaCardCadastrados = new ListaCardCadastrados();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getFragmentManager().beginTransaction();
                args.putBoolean("selecionecard", b);
                args.putString("userID",idUSER);
                args.putString("Nome",usuarioDestinatarioNome);
                args.putString("Usuario", img);
                args.putString("Img", username);


                listaCardCadastrados.setArguments(args);
                fragmentTransaction.replace(R.id.conteinerf, listaCardCadastrados);
                fragmentTransaction.commit();

        }else {
            Toast.makeText(getContext(), "Ocorreu algum erro ao salvar...", Toast.LENGTH_SHORT).show();

        }
    }

}
