package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.adapter_transacoes.view.*
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.R
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model.AllTransactions

class AdapterTransacoes(context: Context, listaTransactions: ArrayList<AllTransactions>) : RecyclerView.Adapter<AdapterTransacoes.MyViewHolder>() {

    private var mContext: Context? = null
    private var mListTransactions: ArrayList<AllTransactions>? = null

    init {
        mContext = context
        mListTransactions = listaTransactions
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_transacoes, parent, false))
    }

    override fun getItemCount(): Int {
        return mListTransactions!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val transaction = mListTransactions!![position]

        Glide.with(mContext).load(transaction.image).into(holder.mImageUser)
        holder.mTextTransaction!!.text = "Você pagou ${transaction.username}"
        holder.mTextValue!!.text = "R$${transaction.timeTransaction}"
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var mImageUser: CircleImageView? = null
        var mTextTransaction: TextView? = null
        var mTextValue: TextView? = null

        init {
            mImageUser = itemView.profile_image
            mTextTransaction = itemView.textTransaction
            mTextValue = itemView.textValue
        }
    }
}