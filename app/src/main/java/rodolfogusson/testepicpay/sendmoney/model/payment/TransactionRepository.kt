package rodolfogusson.testepicpay.sendmoney.model.payment

import androidx.lifecycle.LiveData
import rodolfogusson.testepicpay.core.network.Resource
import rodolfogusson.testepicpay.core.network.ServiceProvider
import rodolfogusson.testepicpay.core.network.request

class TransactionRepository {

    private val service = ServiceProvider.sendMoneyService()

    fun makePayment(paymentRequest: PaymentRequest): LiveData<Resource<PaymentResponse>> =
        request(service.sendPaymentRequest(paymentRequest))
}