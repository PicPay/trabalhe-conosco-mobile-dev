package br.com.devmarques.picpaytestes.Adapter;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.devmarques.picpaytestes.Dados.Pessoas;
import br.com.devmarques.picpaytestes.Fragmentos.Cartao.CadastrarCartao;
import br.com.devmarques.picpaytestes.Fragmentos.Cartao.ListaCardCadastrados;
import br.com.devmarques.picpaytestes.Fragmentos.Cartao.SelecioneOCartao;
import br.com.devmarques.picpaytestes.Fragmentos.Transacao.Transacaos;
import br.com.devmarques.turismo.picpaytestes.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Roger on 08/11/2017.
 */

public class AdapterLista extends RecyclerView.Adapter<AdapterLista.ViewHolder> {

    Context context;
    Bundle args = new Bundle();
    ArrayList<Pessoas> pessoaslist = new ArrayList<Pessoas>();

    public AdapterLista(ArrayList<Pessoas> linktumbs, Context c) {
        this.context = c;
        this.pessoaslist = linktumbs;
    }

    @Override
    public AdapterLista.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflando itens da lista
        View view = LayoutInflater.from(context).inflate(R.layout.row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterLista.ViewHolder holder, final int position) {
        // lisgatem

        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ListaCardCadastrados transacaoCard = new ListaCardCadastrados();
                FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();

                args.putBoolean("selecionecard", true);
                args.putString("userID", Integer.toString(pessoaslist.get(position).getId()));
                args.putString("Nome", pessoaslist.get(position).getNome());
                args.putString("Usuario", pessoaslist.get(position).getUser());
                args.putString("Img", pessoaslist.get(position).getFotoperfil());

                transacaoCard.setArguments(args);

                ft.replace(R.id.conteinerf, transacaoCard).addToBackStack( "tag" );
                ft.commit();


            }
        });

        holder.nome.setText(pessoaslist.get(position).getNome());
        holder.user.setText(pessoaslist.get(position).getUser());

        Picasso.with(context)
                .load(pessoaslist.get(position).getFotoperfil())
                .resize(300, 300)
                .centerCrop()
                .into(holder.circleImageView);


    }

    @Override
    public int getItemCount() {
        return pessoaslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout clicklayout;
        CircleImageView circleImageView;
        //ImageView foto;
        ImageView select;
        TextView nome;
        TextView user;


        public ViewHolder(View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.cicleimg);
            select = itemView.findViewById(R.id.imageclick);
            clicklayout = itemView.findViewById(R.id.layoutssclick);
            //foto = itemView.findViewById(R.id.foto);
            nome = itemView.findViewById(R.id.nome);
            user = itemView.findViewById(R.id.iduser);

        }
    }
}
