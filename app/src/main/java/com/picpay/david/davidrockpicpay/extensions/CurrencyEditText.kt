package com.picpay.david.davidrockpicpay.extensions

import android.content.Context
import android.graphics.Rect
import android.support.v7.appcompat.R
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

/**
 * Some note <br/>
 * <li>Always use locale US instead of default to make DecimalFormat work well in all language</li>
 */
class CurrencyEditText @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.editTextStyle) : android.support.v7.widget.AppCompatEditText(context, attrs, defStyleAttr) {
    private val currencyTextWatcher = CurrencyTextWatcher(this, prefix)

    init {
        this.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        this.hint = prefix
        this.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(MAX_LENGTH))
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (focused) {
            this.addTextChangedListener(currencyTextWatcher)
        } else {
            this.removeTextChangedListener(currencyTextWatcher)
        }
        handleCaseCurrencyEmpty(focused)
    }

    /**
     * When currency empty <br/>
     * + When focus EditText, set the default text = prefix (ex: VND) <br/>
     * + When EditText lose focus, set the default text = "", EditText will display hint (ex:VND)
     */
    private fun handleCaseCurrencyEmpty(focused: Boolean) {
        if (focused) {
            if (text.toString().isEmpty()) {
                setText(prefix)
            }
        } else {
            if (text.toString().equals(prefix)) {
                setText("")
            }
        }
    }

    private class CurrencyTextWatcher internal constructor(editText: EditText, prefix: String) : TextWatcher {
        private val editText: EditText = editText
        private var previousCleanString: String = ""
        private val prefix: String = prefix

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // do nothing
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // do nothing
        }

        override fun afterTextChanged(editable: Editable) {
            val str = editable.toString()
            if (str.length < prefix.length) {
                editText.setText(prefix)
                editText.setSelection(prefix.length)
                return
            }
            if (str == prefix) {
                return
            }
            // cleanString this the string which not contain prefix and ,
            val cleanString = str.replace(prefix, "").replace(("[,]").toRegex(), "")
            // for prevent afterTextChanged recursive call
            if (cleanString == previousCleanString || cleanString.isEmpty()) {
                return
            }
            previousCleanString = cleanString
            val formattedString: String
            if (cleanString.contains(".")) {
                formattedString = formatDecimal(cleanString)
            } else {
                formattedString = formatInteger(cleanString)
            }
            editText.removeTextChangedListener(this) // Remove listener
            editText.setText(formattedString)
            handleSelection()
            editText.addTextChangedListener(this) // Add back the listener
        }

        private fun formatInteger(str: String): String {
            val parsed = BigDecimal(str)
            val formatter = DecimalFormat(prefix + "#,###", DecimalFormatSymbols(Locale.US))
            return formatter.format(parsed)
        }

        private fun formatDecimal(str: String): String {
            if (str == ".") {
                return prefix + "."
            }
            val parsed = BigDecimal(str)
            // example pattern VND #,###.00
            val formatter = DecimalFormat(prefix + "#,###." + getDecimalPattern(str),
                    DecimalFormatSymbols(Locale.US))
            formatter.roundingMode = RoundingMode.DOWN
            return formatter.format(parsed)
        }

        /**
         * It will return suitable pattern for format decimal
         * For example: 10.2 -> return 0 | 10.23 -> return 00, | 10.235 -> return 000
         */
        private fun getDecimalPattern(str: String): String {
            val decimalCount = str.length - str.indexOf(".") - 1
            val decimalPattern = StringBuilder()
            var i = 0
            while (i < decimalCount && i < MAX_DECIMAL) {
                decimalPattern.append("0")
                i++
            }
            return decimalPattern.toString()
        }

        private fun handleSelection() {
            if (editText.text.length <= MAX_LENGTH) {
                editText.setSelection(editText.text.length)
            } else {
                editText.setSelection(MAX_LENGTH)
            }
        }
    }

    companion object {
        private const val prefix = "R$ "
        private const val MAX_LENGTH = 20
        private const val MAX_DECIMAL = 2
    }
}