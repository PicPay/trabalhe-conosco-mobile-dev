package rodolfogusson.testepicpay.core.data

interface Repository<T> {
    fun getData(completion: (Resource<T>) -> Unit)
}