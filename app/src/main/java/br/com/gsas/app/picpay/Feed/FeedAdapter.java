package br.com.gsas.app.picpay.Feed;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.gsas.app.picpay.Domain.Feed;
import br.com.gsas.app.picpay.R;

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Feed> feeds;
    private Context context;

    public FeedAdapter(Context context, List<Feed> feeds){

        this.context = context;
        this.feeds = feeds;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_feed, parent, false);

        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Feed feed = feeds.get(position);

        FeedViewHolder holde = (FeedViewHolder) holder;

        holde.username.setText(feed.getContato().getUsername());
        holde.valor.setText(String.valueOf(feed.getValor()));
        holde.msg.setText(feed.getMsg());

        Picasso.with(context)
                .load(feed.getContato().getImagem())
                .placeholder(R.drawable.fundo_teste)
                .error(R.drawable.fundo_teste)
                .into(holde.imagem);
    }

    @Override
    public int getItemCount() {
        return feeds != null ? feeds.size() : 0;
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder{

        private ImageView imagem;
        private TextView username;
        private TextView valor;
        private TextView msg;

        public FeedViewHolder(View itemView) {
            super(itemView);

            imagem = itemView.findViewById(R.id.imagem_contato);
            username = itemView.findViewById(R.id.username_feed);
            valor = itemView.findViewById(R.id.valor_feed);
            msg = itemView.findViewById(R.id.comentario_feed);
        }
    }
}
