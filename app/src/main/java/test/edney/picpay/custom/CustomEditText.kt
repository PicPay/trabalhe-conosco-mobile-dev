package test.edney.picpay.custom

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.widget.EditText

class CustomEditText(context: Context, att: AttributeSet) : EditText(context, att) {

      override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {
            return super.dispatchKeyEvent(event)
      }
}