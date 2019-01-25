package rodolfogusson.testepicpay.core.utils

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.AndroidViewModel
import com.redmadrobot.inputmask.MaskedTextChangedListener
import rodolfogusson.testepicpay.R
import java.lang.Exception
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


fun String.removeWhitespaces(): String = this.replace("\\s".toRegex(), "")

/**
 * Used to get a string resource inside classes that inherit from AndroidViewModel
 */
fun AndroidViewModel.getString(id: Int): String? {
    return getApplication<Application>().resources.getString(id)
}

/**
 * Shows an AlertDialog, with an error "message", given the appropriate "context".
 */
fun showErrorDialog(message: String, context: Context) {
    AlertDialog.Builder(context)
        .setTitle(context.getString(R.string.error))
        .setMessage(message)
        .setNeutralButton(context.getString(R.string.ok)) { _, _ -> }
        .create()
        .show()
}

/**
 * Customizes the ActionBar to fit the app's layout.
 */
fun ActionBar.customize() {
    title = ""
    setDisplayHomeAsUpEnabled(true)
    setHomeAsUpIndicator(R.drawable.green_back_arrow)
}

/**
 * Used to mask an EditText's input
 */
fun EditText.mask(mask: String) {
    val listener = MaskedTextChangedListener(mask, this)
    this.addTextChangedListener(listener)
    this.onFocusChangeListener = listener
}

/**
 * Property and methods to show/hide keyboard from a View.
 */
private val View.inputMethodManager: InputMethodManager
get() = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

fun View.hideKeyboard() {
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showKeyboard() {
    inputMethodManager.showSoftInput(this, 0)
}


/**
 * Functions to transform from LocalDate to an expiry date string, required by the UI
 * and back to LocalDate.
 */
private val expiryDateFormatter = DateTimeFormatter.ofPattern("MM/yy")

fun LocalDate.asExpiryString(): String {
    return this.format(expiryDateFormatter)
}

fun String.toExpiryDate(): LocalDate? {
    return if (this.length == 5) {
        try {
            val yearMonth = YearMonth.parse(this, expiryDateFormatter)
            // Expiry dates are relative to the last day of its month
            yearMonth.atDay(yearMonth.lengthOfMonth())
        } catch (e: Exception) {
            // Parse from String to LocalDate failed
            null
        }
    } else {
        // String doesn't have 5 characters, as required
        null
    }
}
