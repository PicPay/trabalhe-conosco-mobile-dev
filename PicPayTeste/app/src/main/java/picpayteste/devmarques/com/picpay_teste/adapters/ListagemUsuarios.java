package picpayteste.devmarques.com.picpay_teste.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import picpayteste.devmarques.com.picpay_teste.activitys.CadastrarCartaoCredito;
import picpayteste.devmarques.com.picpay_teste.R;
import picpayteste.devmarques.com.picpay_teste.activitys.Pagamento;
import picpayteste.devmarques.com.picpay_teste.dados.lista.cartao.Cartao;
import picpayteste.devmarques.com.picpay_teste.dados.lista.usuario.Usuario;
import picpayteste.devmarques.com.picpay_teste.dao.cartao.CartaoDaoDB;


public class ListagemUsuarios extends RecyclerView.Adapter<ListagemUsuarios.ViewHolder> {

    private List<Usuario> usuarioslist = new ArrayList<>();
    private Context context;
    private CartaoDaoDB cartaoDaoDB;
    private ArrayList<Cartao> cartao = new ArrayList<>();

    public ListagemUsuarios(List<Usuario> usuarioslist, Context context) {
        this.usuarioslist = usuarioslist;
        this.context = context;
        cartaoDaoDB = new CartaoDaoDB(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.ly_row_userlist, viewGroup, false);


        return new ListagemUsuarios.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        Picasso.get()
                .load(usuarioslist.get(i).getImg())
                .fit()
                .centerInside()
                .into(viewHolder.circleImageView);

        viewHolder.id_usuario.setText(usuarioslist.get(i).getUsername());
        viewHolder.nome.setText(usuarioslist.get(i).getName());

        viewHolder.click_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario usuarioSelecionado;
                usuarioSelecionado =  usuarioslist.get(i);

                cartao = cartaoDaoDB.listar_cartoes();

                if (cartao.isEmpty()) {
                    Intent i = new Intent(context, CadastrarCartaoCredito.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra(Usuario.PARAM_USER_SELECTED, usuarioSelecionado);
                    context.startActivity(i);
                }else{
                    Intent i = new Intent(context, Pagamento.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra(Usuario.PARAM_USER_SELECTED, usuarioSelecionado);
                    i.putExtra(Cartao.PARAM_CARD, cartao.get(0));
                    context.startActivity(i);
                }
            }
        });

    }

    public void filterList(List<Usuario> usuarioList) {
        usuarioslist = usuarioList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return usuarioslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView circleImageView;
        private TextView nome;
        private TextView id_usuario;
        LinearLayout click_list;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            click_list = itemView.findViewById(R.id.user);
            circleImageView = itemView.findViewById(R.id.image_user);
            nome = itemView.findViewById(R.id.nome);
            id_usuario = itemView.findViewById(R.id.nome_id);

        }
    }
}
