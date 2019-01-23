package rodolfogusson.testepicpay.core.ui

import android.app.AlertDialog
import android.content.Context
import android.content.res.Resources
import android.widget.EditText
import androidx.appcompat.app.ActionBar
import com.redmadrobot.inputmask.MaskedTextChangedListener
import rodolfogusson.testepicpay.R

/**
 * Shows an AlertDialog, with an error "message", given the appropriate "context".
 */
fun showErrorDialog(message: String, context: Context) {
    AlertDialog.Builder(context)
        .setTitle(context.getString(R.string.error))
        .setMessage(message)
        .setNeutralButton(context.getString(R.string.ok)){_, _ ->  }
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