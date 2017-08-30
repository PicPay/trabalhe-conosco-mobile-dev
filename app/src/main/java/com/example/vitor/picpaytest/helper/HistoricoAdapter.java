package com.example.vitor.picpaytest.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vitor.picpaytest.R;
import com.example.vitor.picpaytest.dominio.Historico;

import java.util.List;

/**
 * Created by Vitor on 22/08/2017.
 */

public class HistoricoAdapter extends BaseAdapter {

    private final List<Historico> historicos;
    private LayoutInflater inflater;


    public HistoricoAdapter(List<Historico> historicos, Context context) {
        this.historicos = historicos;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return historicos.size();
    }

    @Override
    public Object getItem(int i) {
        return historicos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return historicos.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Historico historico = historicos.get(i);

        view = inflater.inflate(R.layout.historico_item, null);

        TextView username = (TextView) view.findViewById(R.id.username_historico);
        TextView valor = (TextView) view.findViewById(R.id.valor_historico);
        TextView cartao = (TextView) view.findViewById(R.id.cartao_historico);
        ImageView img = (ImageView) view.findViewById(R.id.img_historico);

        byte[] byteArray = historico.getFoto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        img.setImageBitmap(bitmap);

        username.setText(historico.getUsername());
        valor.setText("Valor: R$" + String.valueOf(historico.getValue()));
        cartao.setText("Cartão: " + historico.getCartao());

        return view;
    }
}
