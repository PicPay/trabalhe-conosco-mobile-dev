package rodolfogusson.testepicpay.payment.model.contact

import androidx.lifecycle.LiveData
import rodolfogusson.testepicpay.core.data.Resource
import rodolfogusson.testepicpay.core.network.ServiceGenerator
import rodolfogusson.testepicpay.core.network.request

class ContactRepository {

    private val service = ServiceGenerator.paymentService()
    private val contacts: LiveData<Resource<List<Contact>>>

    init {
        contacts = request(service.getContacts())
    }

    fun getContacts(): LiveData<Resource<List<Contact>>> = contacts
}

