package rodolfogusson.testepicpay.sendpayment.model.contact

import androidx.lifecycle.LiveData
import rodolfogusson.testepicpay.core.network.Resource
import rodolfogusson.testepicpay.core.network.ServiceProvider
import rodolfogusson.testepicpay.core.network.request

class ContactRepository {

    private val service = ServiceProvider.sendPaymentService()

    private val contacts: LiveData<Resource<List<Contact>>>

    init {
        contacts = request(service.getContacts())
    }

    fun getContacts(): LiveData<Resource<List<Contact>>> = contacts
}

