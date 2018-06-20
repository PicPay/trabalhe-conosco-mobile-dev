package br.com.gsas.app.picpay.Contatos;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.gsas.app.picpay.Domain.Contato;
import br.com.gsas.app.picpay.R;

public class ContatoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Contato> contatos;
    private Context context;

    private ContatoOnClickListener contatoOnClickListener;

    public interface  ContatoOnClickListener{

        public void onClickContato(View view, int idx);
    }


    public ContatoAdapter(Context context, List<Contato> contatos, ContatoOnClickListener click){

        this.context = context;
        this.contatos = contatos;
        this.contatoOnClickListener = click;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_contato, parent, false);

        return new ContatoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        final ContatoViewHolder holde = (ContatoViewHolder) holder;
        final Contato contato = contatos.get(position);

        holde.nome.setText(contato.getNome());
        holde.username.setText(contato.getUsername());

        Picasso.with(context)
                .load(contato.getImagem())
                .placeholder(R.drawable.fundo_teste)
                .error(R.drawable.fundo_teste)
                .into(holde.imagem);


        holde.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                contatoOnClickListener.onClickContato(view, holde.getAdapterPosition());

            }
        });


    }

    @Override
    public int getItemCount() {
        return contatos != null ? contatos.size() : 0;
    }

    public class ContatoViewHolder extends RecyclerView.ViewHolder{

        private TextView username;
        private TextView nome;

        private ImageView imagem;
        private LinearLayout linear;

        public ContatoViewHolder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.text_username);
            nome = itemView.findViewById(R.id.text_nome);
            imagem = itemView.findViewById(R.id.imagem_contato);
            linear = itemView.findViewById(R.id.linear_contato);

        }
    }
}
