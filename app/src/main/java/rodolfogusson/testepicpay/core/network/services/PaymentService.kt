package rodolfogusson.testepicpay.core.network.services

import retrofit2.Call
import retrofit2.http.GET
import rodolfogusson.testepicpay.payment.model.contact.Contact

interface PaymentService {
    @GET("users")
    fun getContacts(): Call<List<Contact>>
}