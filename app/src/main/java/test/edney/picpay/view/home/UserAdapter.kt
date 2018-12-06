package test.edney.picpay.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import test.edney.picpay.databinding.ItemUserBinding
import test.edney.picpay.model.UserModel

class UserAdapter(private val listener: UserAdapterListener) : RecyclerView.Adapter<UserAdapter.UserViewModel>() {

    private var data = listOf<UserModel>()

    fun postUsers(data: List<UserModel>){
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewModel {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        binding.ui = listener

        return UserViewModel(binding)
    }

    override fun getItemCount(): Int { return data.size }

    override fun onBindViewHolder(holder: UserViewModel, position: Int) {
        val model = data[position]

        holder.binding.model = model
    }

    class UserViewModel(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)
}