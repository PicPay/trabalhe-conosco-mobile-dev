package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.adapter_notifications.view.*
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.R
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model.AllTransactions

class AdapterNotifications(listaTransactions: ArrayList<AllTransactions>) : RecyclerView.Adapter<AdapterNotifications.MyViewHolder>() {

    private var mListTransactions: ArrayList<AllTransactions>? = null

    init {
        mListTransactions = listaTransactions
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_notifications, parent, false))
    }

    override fun getItemCount(): Int {
        return mListTransactions!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val transactions = mListTransactions!![position]

        holder.mTextResult!!.text = "Seu pagamento a ${transactions.username} é só sucesso :)"
        holder.mTextValue!!.text = "R$${transactions.timeTransaction}"
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var mTextResult: TextView? = null
        var mTextValue: TextView? = null

        init {
            mTextResult = itemView.textResult
            mTextValue = itemView.textValue
        }
    }
}