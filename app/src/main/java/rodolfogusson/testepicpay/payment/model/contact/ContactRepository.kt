package rodolfogusson.testepicpay.payment.model.contact

import android.arch.lifecycle.LiveData
import rodolfogusson.testepicpay.core.data.Resource
import rodolfogusson.testepicpay.core.network.ServiceGenerator
import rodolfogusson.testepicpay.core.network.request
import rodolfogusson.testepicpay.core.network.services.PaymentService

class ContactRepository {

    private val service: PaymentService = ServiceGenerator.paymentService()

    fun getContacts(): LiveData<Resource<List<Contact>>> = request(service.getContacts())
}

