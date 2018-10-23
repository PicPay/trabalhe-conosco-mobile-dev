package stolato.com.br.paypalpayment.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import stolato.com.br.paypalpayment.Model.Card;
import stolato.com.br.paypalpayment.R;

public class CardAdapter extends ArrayAdapter<Card> {

    private Card card;

    public CardAdapter(@NonNull Context context, int resource, @NonNull List<Card> objects) {
        super(context, resource, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        card = getItem(position);
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        convertView = layoutInflater.inflate(R.layout.lista_card,parent,false);
        TextView name = (TextView) convertView.findViewById(R.id.nome);
        name.setText(card.getNome());
        TextView number = (TextView) convertView.findViewById(R.id.number);
        number.setText(card.getNumber());
        TextView expiry = (TextView) convertView.findViewById(R.id.expiry);
        expiry.setText(card.getExpiry());
        return convertView;
    }
}
