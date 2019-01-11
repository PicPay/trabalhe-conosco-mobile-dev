package rodolfogusson.testepicpay.core.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rodolfogusson.testepicpay.core.data.Resource

// Function defined to simplify writing callbacks in rest calls when using Retrofit.
private fun <T> callback(callResponse: (response: Response<T>?,
                                error: Throwable?) -> Unit): Callback<T> {
    return object : Callback<T> {
        override fun onResponse(call: Call<T>?, response: Response<T>?) {
            callResponse(response, null)
        }

        override fun onFailure(call: Call<T>?, t: Throwable?) {
            callResponse(null, t)
        }
    }
}

// Request function to be used in every request made by the app.
fun <T> request(call: Call<T>, completion: (Resource<T>) -> Unit) {
    call.enqueue( callback { response, error ->
        val resource = Resource<T>()

        response?.body()?.let {
            resource.data = it
        }

        error?.let {
            resource.error = it
        }

        completion(resource)
    })
}
