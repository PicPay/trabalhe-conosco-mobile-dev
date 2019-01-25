package rodolfogusson.testepicpay.sendmoney.viewmodel.contact

import android.app.Application
import androidx.lifecycle.*
import rodolfogusson.testepicpay.sendmoney.model.contact.Contact
import rodolfogusson.testepicpay.sendmoney.model.contact.ContactRepository
import rodolfogusson.testepicpay.core.network.Resource
import rodolfogusson.testepicpay.sendmoney.model.creditcard.CreditCard
import rodolfogusson.testepicpay.sendmoney.model.creditcard.CreditCardRepository
import rodolfogusson.testepicpay.sendmoney.model.payment.Transaction
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ContactListViewModel(
    application: Application,
    transaction: Transaction?
) : AndroidViewModel(application) {

    val contacts: LiveData<Resource<List<Contact>>>

    val lastRegisteredCard: LiveData<CreditCard>

    val transactionCompleted = MutableLiveData<Transaction>()

    val transactionDate = MediatorLiveData<String>()

    val transactionTime = MediatorLiveData<String>()

    private val creditCardRepository = CreditCardRepository.getInstance(application)

    private val contactRepository = ContactRepository()

    private lateinit var dateTime: LocalDateTime

    init {
        contacts = contactRepository.getContacts()
        lastRegisteredCard = creditCardRepository.getLastRegisteredCreditCard()

        transactionDate.addSource(transactionCompleted) {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            transactionDate.setValue(dateTime.format(formatter))
        }

        transactionTime.addSource(transactionCompleted) {
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            transactionTime.setValue(dateTime.format(formatter))
        }

        transaction?.let {
            dateTime = Instant.ofEpochSecond(it.timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime()
            transactionCompleted.value = it
        }
    }
}