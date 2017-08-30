package com.example.vitor.picpaytest.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vitor.picpaytest.R;
import com.example.vitor.picpaytest.dominio.Cartao;
import com.example.vitor.picpaytest.persistencia.PicPayDB;

import java.util.List;

/**
 * Created by Vitor on 21/08/2017.
 */


//Adaptador da lsita de Cartoes
public class ListaCartaoAdapter extends BaseAdapter {

    private List<Cartao> cartao;
    private LayoutInflater inflater;

    public ListaCartaoAdapter(List<Cartao> cartao, Context context) {
        this.cartao = cartao;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return cartao.size();
    }

    @Override
    public Object getItem(int i) {
        return cartao.get(i);
    }

    @Override
    public long getItemId(int i) {
        return cartao.get(i).getId();
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final Cartao card = cartao.get(i);

        view = inflater.inflate(R.layout.list_cartao_item, null);

        TextView numero = (TextView) view.findViewById(R.id.numero_list_cartao);
        TextView cvv = (TextView) view.findViewById(R.id.cvv_list_cartao);
        TextView validade = (TextView) view.findViewById(R.id.validade_list_cartao);
        ImageButton botaoExcluir = (ImageButton) view.findViewById(R.id.bt_deletar_list_cartao);

        botaoExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Excluir Cartão");
                builder.setMessage("Você realmente deseja excluir o cartão de sua lista?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PicPayDB bd = new PicPayDB(view.getContext());
                        try{
                            bd.deletarCartao(card);
                            cartao = bd.lista();
                            notifyDataSetChanged();
                            Toast.makeText(view.getContext(), "Cartão Excluído com Sucesso!", Toast.LENGTH_LONG).show();
                        }finally {
                            bd.close();
                        }
                        return;
                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(view.getContext(), "Cartão Mantido!", Toast.LENGTH_LONG).show();
                        return;
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        numero.setText("Numero: " + card.getNumero());
        cvv.setText("CVV: " + card.getCvv());
        validade.setText("Validade: " + card.getVencimento());

        return view;
    }
}
