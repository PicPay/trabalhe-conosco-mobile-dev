package com.picpay.picpayproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.picpay.picpayproject.activities.SendMoneyActivity;
import com.picpay.picpayproject.database.DatabaseHelper;
import com.picpay.picpayproject.model.Cartao;
import com.picpay.picpayproject.model.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by felip on 19/07/2017.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    public static final String AMIGO_ID = "AMIGO_ID";
    public static final String AMIGO_USERNAME = "AMIGO_USERNAME";
    public static final String AMIGO_NOME = "AMIGO_NOME";
    public static final String AMIGO_FOTO = "AMIGO_FOTO";


    private Context context;
    private List<Usuario> usuarioList;

    public Adapter(Context context, List<Usuario> usuarios) {
        this.context = context;
        this.usuarioList = usuarios;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_usuarios,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Usuario usuario = usuarioList.get(position);

        holder.usuario.setText(usuario.getUsername());
        holder.nome.setText(usuario.getNome());
        holder.id.setText(usuario.getId());
        Glide.with(context).load(usuario.getImageLink()).into(holder.foto);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkCard()){
                    Intent i = new Intent(context, SendMoneyActivity.class);
                    i.putExtra(AMIGO_ID,usuario.getId() );
                    i.putExtra(AMIGO_USERNAME,usuario.getUsername() );
                    i.putExtra(AMIGO_NOME,usuario.getNome() );
                    i.putExtra(AMIGO_FOTO,usuario.getImageLink() );

                    context.startActivity(i);
                }else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Ops!")
                            .setMessage("Não há cartão cadastrado, Favor cadastrar!")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return usuarioList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView usuario;
        TextView nome;
        TextView id;
        ImageView foto;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view_usuarios);
            usuario = (TextView) itemView.findViewById(R.id.tv_card_usuario);
            nome = (TextView) itemView.findViewById(R.id.tv_card_nome);
            id = (TextView) itemView.findViewById(R.id.tv_card_id_user);
            foto = (ImageView) itemView.findViewById(R.id.iv_card_foto);
        }
    }

    public Boolean checkCard(){
        Preferences p = new Preferences(context);
        Usuario usuario = new Usuario();
        usuario.setId(p.recuperaid());
        ArrayList<Cartao> listaCartao;

        DatabaseHelper db = new DatabaseHelper(context);
        listaCartao = db.consultarCartao(usuario.getId());

        if (listaCartao.isEmpty()){
            return false;
        }else {
            return true;
        }

    }
}
