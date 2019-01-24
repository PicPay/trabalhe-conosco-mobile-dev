package rodolfogusson.testepicpay.core.network.services

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import rodolfogusson.testepicpay.sendmoney.model.contact.Contact
import rodolfogusson.testepicpay.sendmoney.model.payment.Transaction

interface SendMoneyService {
    @GET("users")
    fun getContacts(): Call<List<Contact>>

    @POST("transaction")
    fun sendTransaction(@Body transaction: Transaction): Call<Unit>
}