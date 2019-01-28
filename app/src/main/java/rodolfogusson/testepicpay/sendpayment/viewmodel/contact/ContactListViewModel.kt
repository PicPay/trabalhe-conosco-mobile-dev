package rodolfogusson.testepicpay.sendpayment.viewmodel.contact

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import rodolfogusson.testepicpay.core.network.Resource
import rodolfogusson.testepicpay.sendpayment.model.contact.Contact
import rodolfogusson.testepicpay.sendpayment.model.contact.ContactRepository
import rodolfogusson.testepicpay.sendpayment.model.creditcard.CreditCard
import rodolfogusson.testepicpay.sendpayment.model.creditcard.CreditCardRepository
import rodolfogusson.testepicpay.sendpayment.model.payment.Transaction
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ContactListViewModel(application: Application) : AndroidViewModel(application) {

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
    }

    fun setTransactionData(transaction: Transaction, creditCard: CreditCard) {
        transactionCompleted.value = transaction
        val dateTime = Instant.ofEpochSecond(transaction.timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime()
        transactionDate.value = dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        transactionTime.value = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        first4NumbersOfCreditCard.value = creditCard.number.take(4)
    }
}