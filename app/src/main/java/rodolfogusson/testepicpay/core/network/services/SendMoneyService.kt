package rodolfogusson.testepicpay.core.network.services

import retrofit2.Call
import retrofit2.http.GET
import rodolfogusson.testepicpay.contacts.model.contact.Contact

interface SendMoneyService {
    @GET("users")
    fun getContacts(): Call<List<Contact>>
}