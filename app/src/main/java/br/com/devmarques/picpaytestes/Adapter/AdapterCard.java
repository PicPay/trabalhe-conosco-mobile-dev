package br.com.devmarques.picpaytestes.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.devmarques.picpaytestes.DAO.CartoesCad.CartaoDAO;
import br.com.devmarques.picpaytestes.Dados.Cartao;
import br.com.devmarques.picpaytestes.Fragmentos.Cartao.ListaCardCadastrados;
import br.com.devmarques.picpaytestes.Fragmentos.Transacao.Transacaos;
import br.com.devmarques.turismo.picpaytestes.R;

/**
 * Created by Roger on 10/11/2017.
 */

public class AdapterCard  extends RecyclerView.Adapter<AdapterCard.ViewHolder> {

    ArrayList<Cartao> cartao = new ArrayList<Cartao>();
    Context context;
    Cartao card;
    Boolean cadastrotransacao;
    String idUSER;
    String usuarioDestinatarioNome, img,username ;

    Bundle args = new Bundle();


    public AdapterCard(ArrayList<Cartao> cartao, Context context, boolean selecioneOcartao) {
        this.cadastrotransacao = selecioneOcartao;
        this.cartao = cartao;
        this.context = context;
    }

    public AdapterCard(ArrayList<Cartao> cartao, Context context, boolean selecioneOcartao,String idUSER,String usuarioDestinatarioNome, String img, String username) {
        this.idUSER = idUSER;
        this.username = username;
        this.usuarioDestinatarioNome = usuarioDestinatarioNome;
        this.img = img;

        this.cadastrotransacao = selecioneOcartao;
        this.cartao = cartao;
        this.context = context;
    }

    @Override
    public AdapterCard.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rowcartao, parent, false);

        if (cadastrotransacao){
            view = LayoutInflater.from(context).inflate(R.layout.primeiratransacao, parent, false);
        }




        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterCard.ViewHolder holder, final int position) {

        if (cartao.get(position).getNomeCard().equals("MasterCard")){
            Picasso.with(context)
                    .load(R.drawable.master)
                    .resize(300, 300)
                    .centerInside()
                    .into(holder.cartao);
        }else{
            Picasso.with(context)
                    .load(R.drawable.visa)
                    .resize(300, 300)
                    .centerCrop()
                    .into(holder.cartao);
        }

        holder.deletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cadastrotransacao){

                    Transacaos transacaoCard = new Transacaos();
                    FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();

                    args.putString("userID", idUSER);
                    args.putString("Nome", usuarioDestinatarioNome);
                    args.putString("Usuario", username);
                    args.putString("Img", img);

                    args.putString("cardnumber", cartao.get(position).getCardNumber());
                    args.putString("dt",  cartao.get(position).getExpirationDate());
                    args.putString("cvv", cartao.get(position).getCvv());
                    args.putString("Bandeira", cartao.get(position).getNomeCard());

                    transacaoCard.setArguments(args);

                    ft.replace(R.id.conteinerf, transacaoCard);
                    ft.commit();

                }else {
                    new AlertDialog.Builder(context)
                            .setMessage("Deseja apagar o cartão " + cartao.get(position).getNomeCard() + " ?")
                            .setCancelable(false)
                            .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    CartaoDAO dao = new CartaoDAO(context);
                                    //nomeCard,String cardNumber, String cvv, String expirationDate
                                    dao.Deletar(new Cartao(cartao.get(position).getId(), cartao.get(position).getNomeCard(), cartao.get(position).getCardNumber(), cartao.get(position).getCvv(), cartao.get(position).getExpirationDate()));

                                    ListaCardCadastrados fragmento_lista = new ListaCardCadastrados();
                                    FragmentTransaction ft = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                                    args.putBoolean("selecionecard",false);
                                    fragmento_lista.setArguments(args);
                                    ft.replace(R.id.conteinerf, fragmento_lista);
                                    ft.commit();
                                }
                            })
                            .setNegativeButton("Não", null)
                            .show();
                }
            }
        });


        holder.nome.setText(cartao.get(position).getNomeCard());

        holder.validade.setText("Validade: "+cartao.get(position).getExpirationDate());

        holder.numeroCartao.setText("XXXX-XXXX-XXXX-" + cartao.get(position).getCardNumber().substring(12,16));


    }

    @Override
    public int getItemCount() {
        return cartao.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView cartao, deletar;
        TextView numeroCartao , nome, validade;


        public ViewHolder(View itemView) {
            super(itemView);

            deletar = itemView.findViewById(R.id.deletarouselecionar);
            cartao = itemView.findViewById(R.id.cartaoimg);
            numeroCartao = itemView.findViewById(R.id.numCard);
            nome = itemView.findViewById(R.id.nomedocard);
            validade = itemView.findViewById(R.id.validade);
        }

    }
}
