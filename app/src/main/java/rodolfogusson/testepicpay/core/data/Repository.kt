package rodolfogusson.testepicpay.core.data

interface Repository<T> {
    fun getData(completion: (T?, Throwable?) -> Unit)
}