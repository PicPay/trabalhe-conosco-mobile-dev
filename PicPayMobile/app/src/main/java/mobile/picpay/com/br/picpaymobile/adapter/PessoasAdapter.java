package mobile.picpay.com.br.picpaymobile.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import mobile.picpay.com.br.picpaymobile.R;
import mobile.picpay.com.br.picpaymobile.activity.PessoasActivity;
import mobile.picpay.com.br.picpaymobile.activity.TransacaoActivity;
import mobile.picpay.com.br.picpaymobile.entity.Pessoa;


public class PessoasAdapter extends RealmRecyclerViewAdapter<Pessoa, PessoasAdapter.MyViewHolder> {
    private OrderedRealmCollection<Pessoa> mList;
    private LayoutInflater mLayoutInflater;
    private Bundle mBundle;
    private Activity activity;
    private Context context;


    public PessoasAdapter(Context c, OrderedRealmCollection<Pessoa> listPessoas, Activity activity) {
        super(listPessoas, true);
        mList = listPessoas;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.item_recycle_pessoas, viewGroup, false);
        MyViewHolder mvh = new MyViewHolder(v);
        context = v.getContext();
        return mvh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int position) {
        myViewHolder.txvNome.setText(mList.get(position).getName());
        myViewHolder.txvId.setText(String.valueOf(mList.get(position).getId()));
        Picasso.with(this.activity)
                .load(mList.get(position).getImg())
                .into(myViewHolder.imagePesList);




        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {//Onclick do item
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TransacaoActivity.class);
                intent.putExtra("idUsuario", myViewHolder.txvId.getText());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    //Modelo do item da lista
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imagePesList;
        public TextView txvNome;
        public TextView txvId;
        public TextView txvEmail;

        public MyViewHolder(View itemView) {
            super(itemView);

            txvNome = (TextView) itemView.findViewById(R.id.txvNome);
            imagePesList = (ImageView) itemView.findViewById(R.id.imagePesList);
            txvId = (TextView) itemView.findViewById(R.id.txvId);

        }
    }
}

