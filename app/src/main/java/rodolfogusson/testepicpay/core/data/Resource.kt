package rodolfogusson.testepicpay.core.data

data class Resource<T>(var data: T? = null, var error: Throwable? = null)