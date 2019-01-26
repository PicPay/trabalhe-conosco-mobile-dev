package rodolfogusson.testepicpay.sendpayment.model.payment

import androidx.lifecycle.LiveData
import rodolfogusson.testepicpay.core.network.Resource
import rodolfogusson.testepicpay.core.network.ServiceProvider
import rodolfogusson.testepicpay.core.network.request

class TransactionRepository {

    private val service = ServiceProvider.sendPaymentService()

    fun makePayment(paymentRequest: PaymentRequest): LiveData<Resource<PaymentResponse>> =
        request(service.sendPaymentRequest(paymentRequest))
}