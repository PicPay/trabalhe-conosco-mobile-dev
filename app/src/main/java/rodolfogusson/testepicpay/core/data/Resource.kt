package rodolfogusson.testepicpay.core.data

/**
 * Wrapper class that encapsulates the data requested from a webservice and
 * some error that may have happened while fetching this resource.
 */
data class Resource<T>(var data: T? = null, var error: Throwable? = null)