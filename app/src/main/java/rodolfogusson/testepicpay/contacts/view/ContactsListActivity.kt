package rodolfogusson.testepicpay.contacts.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_contact_list.*
import rodolfogusson.testepicpay.R
import rodolfogusson.testepicpay.contacts.viewmodel.ContactsListViewModel
import rodolfogusson.testepicpay.core.ui.showErrorDialog
import rodolfogusson.testepicpay.databinding.ActivityContactListBinding

class ContactsListActivity : AppCompatActivity() {

    private lateinit var contactsListViewModel: ContactsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_list)

        contactsListViewModel = ViewModelProviders.of(this)
            .get(ContactsListViewModel::class.java)

        ActivityContactListBinding.inflate(layoutInflater)
            .apply {
                setLifecycleOwner(this@ContactsListActivity)
                viewModel = contactsListViewModel
            }
        setupLayout()
        setupRecyclerView()
        registerObserver()
        contactsListViewModel.getUsers()
    }

    private fun setupRecyclerView() {
        val animals: ArrayList<String> = ArrayList()
        animals.add("dog")
        animals.add("cat")
        animals.add("owl")
        animals.add("cheetah")
        animals.add("raccoon")
        animals.add("bird")
        animals.add("snake")
        animals.add("lizard")
        animals.add("hamster")
        animals.add("bear")
        animals.add("lion")
        animals.add("tiger")
        animals.add("horse")
        animals.add("frog")
        animals.add("fish")
        animals.add("shark")
        animals.add("turtle")
        animals.add("elephant")
        animals.add("cow")
        animals.add("beaver")
        animals.add("bison")
        animals.add("porcupine")
        animals.add("rat")
        animals.add("mouse")
        animals.add("goose")
        animals.add("deer")
        animals.add("fox")
        animals.add("moose")
        animals.add("buffalo")
        animals.add("monkey")
        animals.add("penguin")
        animals.add("parrot")

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyAdapter(animals, this)
        recyclerView.isNestedScrollingEnabled = false
    }

    private fun setupLayout() {
        supportActionBar?.hide()
    }

    private fun registerObserver() {
        contactsListViewModel.users.observe(this, Observer {
            it?.error?.let { error ->
                showErrorDialog(error.localizedMessage, this)
                return@Observer
            }
            it?.data?.let { list ->
//                for (item in list) {
//                    tv.append(item.username + " ")
//                }
            }
        })
    }
}
