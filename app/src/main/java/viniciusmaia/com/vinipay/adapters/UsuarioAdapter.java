package viniciusmaia.com.vinipay.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import viniciusmaia.com.vinipay.R;
import viniciusmaia.com.vinipay.activities.TransacaoActivity;
import viniciusmaia.com.vinipay.modelo.Usuario;

/**
 * Created by User on 03/12/2017.
 */

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> {

    private List<Usuario> listaUsuarios;
    private Context context;

    public UsuarioAdapter(List<Usuario> usuarios, Context context){
        listaUsuarios = usuarios;
        this.context = context;
    }

    @Override
    public UsuarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UsuarioViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.usuario_item, parent, false));
    }

    @Override
    public void onBindViewHolder(UsuarioViewHolder holder, int position) {
        if (listaUsuarios != null && listaUsuarios.size() > 0){
            Usuario usuario = listaUsuarios.get(position);

            if (usuario != null){
                Picasso.with(context).load(usuario.getImagemUrl()).into(holder.imagemPerfil);
                holder.textNomeUsuario.setText(usuario.getNome());
                holder.textUsuario.setText(usuario.getUsuario());
            }
        }
    }

    @Override
    public int getItemCount() {
        return listaUsuarios != null ? listaUsuarios.size() : 0;
    }

    public class UsuarioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textUsuario;
        public TextView textNomeUsuario;
        public CircleImageView imagemPerfil;
        private final Context context;
        //private RecyclerItemClickListener itemClickListener;

        public UsuarioViewHolder(View itemView){
            super(itemView);

            textUsuario = (TextView)itemView.findViewById(R.id.textUsuario);
            textNomeUsuario = (TextView)itemView.findViewById(R.id.textNomeUsuario);
            imagemPerfil = (CircleImageView)itemView.findViewById(R.id.imagemPerfil);
            context = itemView.getContext();

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Usuario usuario = listaUsuarios.get(getAdapterPosition());
            Intent intentTransacao = new Intent(view.getContext(), TransacaoActivity.class);
            intentTransacao.putExtra("idUsuario", usuario.getId());
            intentTransacao.putExtra("usuario", usuario.getUsuario());

            context.startActivity(intentTransacao);
        }
    }
}
