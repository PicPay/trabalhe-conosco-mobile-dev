package com.jvtnascimento.picpay.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jvtnascimento.picpay.R
import com.jvtnascimento.picpay.models.User
import kotlinx.android.synthetic.main.item_user.view.*
import android.widget.Filter
import android.widget.Filterable
import com.jvtnascimento.picpay.dagger.modules.GlideApp
import com.jvtnascimento.picpay.ui.views.PaymentActivity


class UserAdapter(private val items : ArrayList<User>, private val context: Context) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>(),
    Filterable {


    var onClick: (User) -> Unit = {}

    internal var filterListResult: ArrayList<User> = ArrayList()

    init {
        this.filterListResult = items
    }

    override fun getItemCount(): Int {
        return filterListResult.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_user, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = filterListResult[position]

        GlideApp.with(context)
            .load(user.img)
            .placeholder(R.color.imageViewBackground)
            .into(holder.userPicture)

        holder.userName.text = user.name
        holder.userUsername.text = user.username

        holder.itemView.setOnClickListener {
            this.onClick(user)
        }
    }

    override fun getFilter(): Filter {
        return object: Filter() {
            override fun performFiltering(charString: CharSequence?): FilterResults {
                val charSearch: String = charString.toString()

                if (charSearch.isEmpty())
                    filterListResult = items
                else {
                    val resultList = ArrayList<User>()
                    for (row in items) {
                        if (row.name.toLowerCase().contains(charSearch.toLowerCase()))
                            resultList.add(row)
                    }

                    filterListResult =  resultList
                }

                val filterResults: FilterResults = Filter.FilterResults()
                filterResults.values = filterListResult
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults?) {
                filterListResult = filterResults!!.values as ArrayList<User>
                notifyDataSetChanged()
            }
        }
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val userName = view.userName!!
        val userUsername = view.userUsername!!
        val userPicture = view.userPicture!!
    }
}