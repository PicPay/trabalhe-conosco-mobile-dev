package rodolfogusson.testepicpay.core.network

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rodolfogusson.testepicpay.core.data.Resource

// Function defined to simplify writing callbacks in Retrofit network calls.
private fun <T> callback(
    callResponse: (
        response: Response<T>?,
        error: Throwable?
    ) -> Unit
): Callback<T> {
    return object : Callback<T> {
        override fun onResponse(call: Call<T>?, response: Response<T>?) {
            callResponse(response, null)
        }

        override fun onFailure(call: Call<T>?, t: Throwable?) {
            callResponse(null, t)
        }
    }
}

// Function used to make every network request in the application.
fun <T> request(call: Call<T>): LiveData<Resource<T>> {
    val liveData = MutableLiveData<Resource<T>>()
    call.enqueue(callback { response, error ->
        val resource = Resource(response?.body(), error)
        liveData.value = resource
    })
    return liveData
}
