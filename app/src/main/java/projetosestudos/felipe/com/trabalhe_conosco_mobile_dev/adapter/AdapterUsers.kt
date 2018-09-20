package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.adapter_users.view.*
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.R
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.activity.PaymentActivity
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model.User

class AdapterUsers : RecyclerView.Adapter<AdapterUsers.MyViewHolder> {

    var mListUser: ArrayList<User>? = null
    var mContext: Context? = null

    constructor(context: Context, listUser: ArrayList<User>) {
        mContext = context
        mListUser = listUser
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_users, parent, false))
    }

    override fun getItemCount(): Int {
        return mListUser!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = mListUser!![position]
        holder.mTextNomeUsuario!!.text = user.username

        Glide.with(mContext).load(user.img).into(holder.mImageUsuario)

        holder.itemView.setOnClickListener {
            val intent = Intent(mContext, PaymentActivity::class.java)
            intent.putExtra("user", user)
            mContext!!.startActivity(intent)
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var mImageUsuario: CircleImageView? = null
        var mTextNomeUsuario: TextView? = null

        init {
            mImageUsuario = itemView.profile_image
            mTextNomeUsuario = itemView.textNomeUsuario
        }
    }
}