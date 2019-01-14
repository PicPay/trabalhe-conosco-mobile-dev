package rodolfogusson.testepicpay.payment.model.contact

import rodolfogusson.testepicpay.core.data.Repository
import rodolfogusson.testepicpay.core.data.Resource
import rodolfogusson.testepicpay.core.network.ServiceGenerator
import rodolfogusson.testepicpay.core.network.request
import rodolfogusson.testepicpay.core.network.services.PaymentService

class ContactRepository: Repository<List<Contact>> {

    private val service: PaymentService = ServiceGenerator.paymentService()

    override fun getData(completion: (Resource<List<Contact>>) -> Unit) = request(service.getContacts(), completion)
}

