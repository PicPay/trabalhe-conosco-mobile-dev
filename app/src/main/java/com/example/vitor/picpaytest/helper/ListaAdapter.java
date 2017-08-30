package com.example.vitor.picpaytest.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vitor.picpaytest.R;
import com.example.vitor.picpaytest.dominio.Usuario;

import java.util.List;

/**
 * Created by Vitor on 19/08/2017.
 */


//Adaptador lista de contatos
public class ListaAdapter extends BaseAdapter{

    private final List<Usuario> usuarios;
    private LayoutInflater inflater;


    public ListaAdapter(List<Usuario> usuarios, Context context) {
        this.usuarios = usuarios;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return usuarios.size();
    }

    @Override
    public Object getItem(int i) {
        return usuarios.get(i);
    }

    @Override
    public long getItemId(int i) {
        return usuarios.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Usuario usuario = usuarios.get(i);

        view = inflater.inflate(R.layout.list_item, null);

        TextView username = (TextView) view.findViewById(R.id.username);
        TextView name = (TextView) view.findViewById(R.id.name);
        ImageView img = (ImageView) view.findViewById(R.id.img);


        username.setText(usuario.getUsername());
        name.setText(usuario.getName());

        img.setImageBitmap(usuario.getImagem());

        return view;
    }
}
