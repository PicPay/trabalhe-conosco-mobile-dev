package rodolfogusson.testepicpay.sendpayment.viewmodel.contact

import android.app.Application
import androidx.lifecycle.*
import rodolfogusson.testepicpay.sendpayment.model.contact.Contact
import rodolfogusson.testepicpay.sendpayment.model.contact.ContactRepository
import rodolfogusson.testepicpay.core.network.Resource
import rodolfogusson.testepicpay.sendpayment.model.creditcard.CreditCard
import rodolfogusson.testepicpay.sendpayment.model.creditcard.CreditCardRepository
import rodolfogusson.testepicpay.sendpayment.model.payment.Transaction
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ContactListViewModel(
    application: Application,
    transaction: Transaction?,
    cardUsedInTransaction: CreditCard?
) : AndroidViewModel(application) {

    val contacts: LiveData<Resource<List<Contact>>>

    val lastRegisteredCard: LiveData<CreditCard>

    val transactionCompleted = MutableLiveData<Transaction>()

    val transactionDate = MutableLiveData<String>()

    val transactionTime = MutableLiveData<String>()

    val first4NumbersOfCreditCard = MutableLiveData<String>()

    private val creditCardRepository = CreditCardRepository.getInstance(application)

    private val contactRepository = ContactRepository()

    init {
        contacts = contactRepository.getContacts()
        lastRegisteredCard = creditCardRepository.getLastRegisteredCreditCard()

        transaction?.let {
            transactionCompleted.value = it
            var dateTime = Instant.ofEpochSecond(it.timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime()
            transactionDate.value = dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            transactionTime.value = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        }

        cardUsedInTransaction?.let {
            first4NumbersOfCreditCard.value = cardUsedInTransaction.number.take(4)
        }
    }
}