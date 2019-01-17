package rodolfogusson.testepicpay.core.data

import retrofit2.Response

/**
 * Wrapper class that encapsulates the data requested from a webservice and
 * some error that may have happened while fetching this resource.
 */
data class Resource<T>(private var response: Response<T>? = null,
                       private var exception: Throwable? = null) {
    var data: T? = null
    var error: Throwable? = null

    init {
        exception?.let {
            error = it
        }
        response?.let {
            if (it.isSuccessful) {
                data = it.body()
            } else {
                error = Throwable("${it.code()}: ${it.message()}")
            }
        }
    }
}