package br.com.gsas.app.picpay.Carteira.Cartao;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import br.com.gsas.app.picpay.Domain.Cartao;
import br.com.gsas.app.picpay.R;

public class CartaoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int VIEW_ITEM = 1;
    private final int VIEW_VAZIO = 0;

    private List<Cartao> cartaos;
    private Context context;
    private List<Boolean> editar;

    private CartaoClickListener click;

    public interface CartaoClickListener{

        public void onClickVazio(View view);
        public void onClickCartao(View view, int pos);
        public void onClickDelete(View view, int pos);
        public void onClickPadrao(View view, int pos);

    }

    public CartaoAdapter(Context context, List<Cartao> cartaos, CartaoClickListener click, List<Boolean> editar){

        Log.d("Cartao", "Criou");
        this.context = context;
        this.cartaos = cartaos;
        this.click = click;
        this.editar = editar;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == VIEW_ITEM) {

            View view = LayoutInflater.from(context).inflate(R.layout.adapter_cartao, parent, false);

            return new CartaoViewHolder(view);

        }
        else {

            View view = LayoutInflater.from(context).inflate(R.layout.adapter_cartao_vazio, parent, false);

            return new VazioCartaoViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder.getItemViewType() == VIEW_ITEM) {

            final CartaoViewHolder holde = (CartaoViewHolder) holder;

            Cartao cartao = cartaos.get(position);

            String numero = cartao.getCard_nunber();
            holde.final_cartao.setText(numero.substring(numero.length() - 4, numero.length()));

            if(!editar.get(0)){

                holde.delete_cartao.setVisibility(View.GONE);

                holde.layout_cartao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        click.onClickPadrao(view, holde.getAdapterPosition());
                    }
                });
            }
            else{

                holde.delete_cartao.setVisibility(View.VISIBLE);
            }
            if(cartao.getAtivo() == 1){
                holde.check_cartao.setVisibility(View.VISIBLE);
            }
            else{
                holde.check_cartao.setVisibility(View.INVISIBLE);
            }

            holde.delete_cartao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    click.onClickDelete(view, holde.getAdapterPosition());
                }
            });

        }
        else{

            Log.d("Cartao", "Chamou");

            VazioCartaoViewHolder holde = (VazioCartaoViewHolder) holder;

            holde.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    click.onClickVazio(view);
                }
            });
        }
    }

    @Override
    public int getItemCount() {

        if(cartaos == null || cartaos.size() == 0){
            return 1;
        }
        else {
            return cartaos.size();
        }

    }

    @Override
    public int getItemViewType(int position) {

        if(cartaos == null || cartaos.size() == 0){
            return VIEW_VAZIO;
        }
        else {
            return VIEW_ITEM;
        }

    }

    private class VazioCartaoViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout linearLayout;

        public VazioCartaoViewHolder(View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.layout_cartaoVazio);
        }
    }

    private class CartaoViewHolder extends RecyclerView.ViewHolder{

        private ImageView delete_cartao;
        private TextView final_cartao;
        private ImageView check_cartao;
        private RelativeLayout layout_cartao;

        public CartaoViewHolder(View itemView) {
            super(itemView);

            delete_cartao = itemView.findViewById(R.id.delete_cartao);
            final_cartao = itemView.findViewById(R.id.final_cartao);
            check_cartao = itemView.findViewById(R.id.check_cartao);
            layout_cartao = itemView.findViewById(R.id.layout_cartao);

        }
    }
}
