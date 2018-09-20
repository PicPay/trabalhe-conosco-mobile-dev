package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.adapter_my_cards.view.*
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.R
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model.UserCards

class AdapterMyAllCards(context: Context, listaCards: ArrayList<UserCards>) : RecyclerView.Adapter<AdapterMyAllCards.MyViewHolder>() {

    private var mContext: Context? = null
    private var mListCards: ArrayList<UserCards>? = null

    init {
        mContext = context
        mListCards = listaCards
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_my_cards, parent, false))
    }

    override fun getItemCount(): Int {
        return mListCards!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userCards = mListCards!![position]

        holder.mTextMyCardNumber!!.text = "**** **** **** ${userCards.cardNumber.substring(15)}"
        holder.mTextMyCardName!!.text = userCards.cardName
        holder.mTextMyCardValidate!!.text = userCards.card_validate
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder((itemView)) {

        var mTextMyCardNumber: TextView? = null
        var mTextMyCardName: TextView? = null
        var mTextMyCardValidate: TextView? = null

        init {
            mTextMyCardNumber = itemView.textMyCardNumber
            mTextMyCardName = itemView.textMyCardName
            mTextMyCardValidate = itemView.textMyCardValidate
        }
    }
}