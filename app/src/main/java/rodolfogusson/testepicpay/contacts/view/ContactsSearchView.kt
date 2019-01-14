package rodolfogusson.testepicpay.contacts.view

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Checkable
import android.widget.EditText
import android.widget.RelativeLayout
import rodolfogusson.testepicpay.R

class ContactsSearchView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    val searchEditText: EditText

    init {
        View.inflate(context, R.layout.search_view, this)
        searchEditText = findViewById(R.id.searchEditText)
        searchEditText.setOnFocusChangeListener(::editTextFocusChanged)
        this.setOnClickListener(::onClick)
    }

    private fun editTextFocusChanged(view: View?, hasFocus: Boolean) {
        this.isSelected = hasFocus
    }

    private fun onClick(v: View){
        searchEditText.requestFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(searchEditText, 0)
    }
}