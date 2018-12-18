package br.com.kassianoresende.picpay.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.kassianoresende.picpay.R
import br.com.kassianoresende.picpay.model.CreditCard

class CardsAdapter: RecyclerView.Adapter<CardsAdapter.ViewHolder>() {

    private lateinit var cardList: List<CreditCard>

    var itemClick: (CreditCard)->Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {

        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_card, parent, false)

        return ViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return if (::cardList.isInitialized) {
            cardList.size
        } else {
            0
        }
    }

    fun updateCardList(cardList: List<CreditCard>){
        this.cardList = cardList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvCardFlag.text = cardList[position].flag
        holder.tvCardNumber.text = cardList[position].cardNumber
        holder.view.setOnClickListener {
            itemClick(cardList[position])
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val view = itemView
        val tvCardFlag =  itemView.findViewById<TextView>(R.id.tvCardFlag)
        val tvCardNumber = itemView.findViewById<TextView>(R.id.tvCardNumber)
    }
}
