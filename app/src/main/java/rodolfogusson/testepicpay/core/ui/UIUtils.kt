package rodolfogusson.testepicpay.core.ui

import android.app.AlertDialog
import android.content.Context
import rodolfogusson.testepicpay.R

fun showErrorDialog(message: String, context: Context) {
    AlertDialog.Builder(context)
        .setTitle(context.getString(R.string.error))
        .setMessage(message)
        .setNeutralButton(context.getString(R.string.ok)){_, _ ->  }
        .create()
        .show()
}