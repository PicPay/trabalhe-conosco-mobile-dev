package rodolfogusson.testepicpay.core.network.services

import retrofit2.Call
import retrofit2.http.GET
import rodolfogusson.testepicpay.contacts.model.user.User

interface SendMoneyService {
    @GET("users")
    fun getUsers(): Call<List<User>>
}