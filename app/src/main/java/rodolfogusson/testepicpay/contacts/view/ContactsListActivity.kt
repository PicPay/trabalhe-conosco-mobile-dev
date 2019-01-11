package rodolfogusson.testepicpay.contacts.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_contact_list.*
import rodolfogusson.testepicpay.R
import rodolfogusson.testepicpay.contacts.viewmodel.ContactsListViewModel
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

        contactsListViewModel.data.observe(this, Observer {
            //            if(it != null) {
//                for(item in it) {
//                    textView.append(item.username + " ")
//                }
//            }

            if (it != null) {
                if (it.response != null) {
                    val ir = it.response
                    for (item in ir!!) {
                        textView.append(item.username + " ")
                    }
                }
            }

//            it?.error?.let {
//                //show error
//                return@Observer
//            }
//            it?.response?.let {
//                for(item in it) {
//                    textView.append(item.username + " ")
//                }
//            }
        })

        contactsListViewModel.getUsers()
    }
}
