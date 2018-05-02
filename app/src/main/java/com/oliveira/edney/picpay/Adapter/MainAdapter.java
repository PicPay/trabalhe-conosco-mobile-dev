package com.oliveira.edney.picpay.Adapter;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.oliveira.edney.picpay.ImageURL;
import com.oliveira.edney.picpay.ItemClickListener;
import com.oliveira.edney.picpay.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* Adapter da lista de pessoas */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private static ItemClickListener clickListener;
    private JSONArray data;

    public MainAdapter(){
        data = new JSONArray();
    }

    public void setOnItemClickListener(ItemClickListener clickListener){
        MainAdapter.clickListener = clickListener;
    }

    /* Data de pessoas */
    public void setData(JSONArray data){
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pessoas, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        try {

            JSONObject pessoa = data.getJSONObject(position);

            /* Carrega as fotos por url */
            new ImageURL(new ImageURL.Callback() {
                @Override
                public void onLoaded(Bitmap bitmap) {

                    holder.ivFoto.setImageBitmap(bitmap);
                }
            }).execute(pessoa.getString("img"));

            holder.tvNome.setText(pessoa.getString("name"));
            holder.tvId.setText(String.format("id: %s", pessoa.getString("id")));
            holder.tvUsername.setText(pessoa.getString("username"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.length();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView ivFoto;
        private TextView tvNome;
        private TextView tvId;
        private TextView tvUsername;

        ViewHolder(View v){
            super(v);

            ivFoto = v.findViewById(R.id.iv_item_foto);
            tvNome = v.findViewById(R.id.tv_item_nome);
            tvId = v.findViewById(R.id.tv_item_id);
            tvUsername = v.findViewById(R.id.tv_item_username);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if(clickListener != null){
                clickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
