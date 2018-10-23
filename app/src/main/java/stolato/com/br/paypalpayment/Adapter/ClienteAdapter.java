package stolato.com.br.paypalpayment.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import stolato.com.br.paypalpayment.Model.Cliente;
import stolato.com.br.paypalpayment.R;

/**
 * Created by luiz on 19/06/17.
 */

public class ClienteAdapter extends ArrayAdapter<Cliente> {

    private Cliente cliente;

    public ClienteAdapter(@NonNull Context context, @LayoutRes int resource,List<Cliente> cliente) {
        super(context, resource,cliente);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        cliente = getItem(position);
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        convertView = layoutInflater.inflate(R.layout.lista_clientes,parent,false);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.thumb);
        Picasso.get().load(cliente.getImg()).into(imageView);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        name.setText(cliente.getName());
        TextView user = (TextView) convertView.findViewById(R.id.username);
        user.setText(cliente.getUsername());
        return convertView;
    }

}
