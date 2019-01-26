package rodolfogusson.testepicpay.sendpayment.model.contact
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
    val id: Int,
    val img: String,
    val name: String,
    val username: String
) : Parcelable {
    companion object { const val key = "CONTACT_KEY" }
}