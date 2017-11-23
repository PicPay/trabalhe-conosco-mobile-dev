package br.com.devmarques.picpaytestes.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.devmarques.picpaytestes.Dados.Cartao;
import br.com.devmarques.picpaytestes.Dados.Transacoes;
import br.com.devmarques.turismo.picpaytestes.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Roger on 12/11/2017.
 */

public class AdapterTransacoes extends RecyclerView.Adapter<AdapterTransacoes.ViewHolder> {

    ArrayList<Transacoes> transacoesList = new ArrayList<Transacoes>();
    Context context;
    Transacoes transacoes;
    LinearLayout aprovadoOUnao;

    public AdapterTransacoes(ArrayList<Transacoes> transacoesList, Context context) {
        this.transacoesList = transacoesList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rowtransacoes, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterTransacoes.ViewHolder holder, int position) {

        holder.nome.setText("Transferido para: "+transacoesList.get(position).getNome()+".");
        holder.transacaoNum.setText("Transação Nº: "+transacoesList.get(position).getTransacaoNum()+".");
        holder.valor.setText("Valor da Transferência R$:"+transacoesList.get(position).getValor()+".");
        holder.aprovado.setText("Aprovado: "+transacoesList.get(position).getAprovada()+".");
        holder.cartaousadoEbandeira.setText("Cartão usado final: "+transacoesList.get(position).getCardNameandNumber()+".");

        retornacor(transacoesList.get(position).getStatus());


        Picasso.with(context)
                .load(transacoesList.get(position).getFotoperfil())
                .resize(300, 300)
                .centerCrop()
                .into(holder.circleImageView);

    }

    @Override
    public int getItemCount() {
        return transacoesList.size();
    }

    public void retornacor(String value){
        if (value.equals("false")){
            aprovadoOUnao.setBackgroundResource(R.drawable.radiusv);
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

         CircleImageView circleImageView;

         TextView transacaoNum, nome, valor,aprovado, cartaousadoEbandeira;

        public ViewHolder(View itemView) {
            super(itemView);
            aprovadoOUnao = itemView.findViewById(R.id.statustransacao);
            circleImageView = itemView.findViewById(R.id.fotouserTransacao);
            transacaoNum = itemView.findViewById(R.id.numeroTransacao);
            nome = itemView.findViewById(R.id.nomedaPessoaTransferida);
            valor = itemView.findViewById(R.id.valordaTransferencia);
            aprovado = itemView.findViewById(R.id.aprovado);
            cartaousadoEbandeira = itemView.findViewById(R.id.cartaousadotransferencia);



        }
    }
}
