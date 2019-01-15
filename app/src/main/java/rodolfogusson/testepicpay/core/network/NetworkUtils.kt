package rodolfogusson.testepicpay.core.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rodolfogusson.testepicpay.core.data.Resource

/**
 * Simplifies writing callbacks in Retrofit network calls,
 * using this function and passing a lambda expression
 * as argument.
 */
private fun <T> callback(
    callResponse: (response: Response<T>?, error: Throwable?) -> Unit
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

/**
 * Used to make every network request in the application.
 * Initially returns a reference to a LiveData, that will
 * be populated in the future, inside the callback,
 * with a Resource<T>.
 */
fun <T> request(call: Call<T>): LiveData<Resource<T>> {
    val liveData = MutableLiveData<Resource<T>>()
    call.enqueue(callback { response, error ->
        val resource = Resource(response?.body(), error)
        liveData.value = resource
    })
    return liveData
}
