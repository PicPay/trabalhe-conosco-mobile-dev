package rodolfogusson.testepicpay.contacts.model.user

import rodolfogusson.testepicpay.core.data.Repository
import rodolfogusson.testepicpay.core.network.ServiceGenerator
import rodolfogusson.testepicpay.core.network.request

class UserRepository: Repository<List<User>> {

    val service = ServiceGenerator.sendMoneyService()

    override fun getData(completion: (List<User>?, Throwable?) -> Unit) = request(service.getUsers(), completion)
}

