package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.adapter_pagar_usuarios.view.*
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.R
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.activity.PaymentActivity
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model.User

class AdapterPagarUsuarios(context: Context, listUsers: ArrayList<User>) : RecyclerView.Adapter<AdapterPagarUsuarios.MyViewHolder>() {

    private var mContext: Context? = null
    private var mListUsers: ArrayList<User>? = null

    init {
        mContext = context
        mListUsers = listUsers
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_pagar_usuarios, parent, false))
    }

    override fun getItemCount(): Int {
        return mListUsers!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = mListUsers!![position]

        Glide.with(mContext).load(user.img).into(holder.circlePagarUser)
        holder.textUsername!!.text = user.username
        holder.textNomeUsuario!!.text = user.name

        holder.itemView.setOnClickListener {
            val intent = Intent(mContext, PaymentActivity::class.java)
            intent.putExtra("user", user)
            mContext!!.startActivity(intent)
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var circlePagarUser: CircleImageView? = null
        var textUsername: TextView? = null
        var textNomeUsuario: TextView? = null

        init {
            circlePagarUser = itemView.circlePagarUser
            textUsername = itemView.textUsername
            textNomeUsuario = itemView.textNomeusuario
        }

    }
}