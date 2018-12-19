package test.edney.picpay.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import test.edney.picpay.databinding.ItemUserBinding
import test.edney.picpay.model.UserModel

class UserAdapter(private val listener: UserAdapterListener) : RecyclerView.Adapter<UserAdapter.UserViewModel>() {

      private var data = listOf<UserModel>()
      private var filteredData = listOf<UserModel>()

      fun postUsers(data: List<UserModel>) {
            this.data = data
            filteredData = data
            notifyDataSetChanged()
      }

      override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewModel {
            val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            binding.ui = listener

            return UserViewModel(binding)
      }

      override fun getItemCount(): Int {
            return filteredData.size
      }

      override fun onBindViewHolder(holder: UserViewModel, position: Int) {
            val model = filteredData[position]

            holder.binding.model = model
      }

      fun getFilter() = object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                  val toFind = constraint.toString().toLowerCase()
                  val filtered = mutableListOf<UserModel>()
                  val results = FilterResults()

                  for (model: UserModel in data) {
                        if (model.name != null) {
                              val compare = model.name?.toLowerCase()

                              if (compare!!.contains(toFind))
                                    filtered.add(model)
                        }
                  }

                  results.values = filtered

                  return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                  if (results?.values != null) {
                        @Suppress("UNCHECKED_CAST")
                        filteredData = results.values as List<UserModel>
                        notifyDataSetChanged()
                  }
            }
      }

      class UserViewModel(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)
}