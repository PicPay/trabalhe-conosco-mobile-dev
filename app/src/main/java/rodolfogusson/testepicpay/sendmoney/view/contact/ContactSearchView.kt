package rodolfogusson.testepicpay.sendmoney.view.contact

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RelativeLayout
import rodolfogusson.testepicpay.R

class ContactSearchView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private val searchEditText: EditText
    private val searchDrawable = context.getDrawable(R.drawable.ic_search)
    private val closeDrawable = context.getDrawable(R.drawable.ic_close)
    private val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    var search: ((String) -> Unit)? = null

    init {
        View.inflate(context, R.layout.search_view, this)
        searchEditText = findViewById(R.id.searchEditText)
        searchEditText.setOnFocusChangeListener(::editTextFocusChanged)
        searchEditText.setOnTouchListener(::onRightDrawableTouched)
        this.setOnClickListener(::onContainerViewClicked)
        observeEditText()
    }

    private fun editTextFocusChanged(view: View?, hasFocus: Boolean) {
        this.isSelected = hasFocus
        if (view is EditText) changeWidth(view, hasFocus)
    }

    private fun changeWidth(editText: EditText, hasFocus: Boolean) {
        if (hasFocus) {
            editText.layoutParams.width = MATCH_PARENT
            editText.setCompoundDrawablesWithIntrinsicBounds(
                searchDrawable,
                null,
                closeDrawable,
                null)
        } else {
            editText.layoutParams.width = WRAP_CONTENT
            editText.setCompoundDrawablesWithIntrinsicBounds(
                searchDrawable,
                null,
                null,
                null)
        }
    }

    private fun onRightDrawableTouched(v: View, event: MotionEvent): Boolean {
        // Other drawable indexes declared for the sake of readability
        val DRAWABLE_LEFT = 0
        val DRAWABLE_TOP = 1
        val DRAWABLE_RIGHT = 2
        val DRAWABLE_BOTTOM = 3

        if (v !is EditText || v.compoundDrawables[DRAWABLE_RIGHT] == null) return false

        if(event.x >= (v.width - v.compoundDrawables[DRAWABLE_RIGHT].bounds.width())) {
            clearEditText(v)
            return true
        }
        return false
    }

    private fun onContainerViewClicked(v: View){
        searchEditText.requestFocus()
        imm.showSoftInput(searchEditText, 0)
    }

    private fun observeEditText() {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                search?.let {
                    it(s.toString())
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun clearEditText(v: View) {
        searchEditText.setText("")
        searchEditText.clearFocus()
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }
}