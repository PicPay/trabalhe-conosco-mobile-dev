package rodolfogusson.testepicpay.core.ui

import android.view.View
import androidx.databinding.BindingAdapter
import android.widget.ImageView
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Picasso
import com.google.android.material.textfield.TextInputLayout
import android.text.TextWatcher
import android.widget.EditText





/**
 * Function defined to enable the use of the parameter app:imageUrl = ...
 * in xml files using DataBinding.
 */
@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    Picasso
        .get()
        .load(url)
        .into(imageView)
}

//@BindingAdapter("onFocusChange")
//fun setFocusChangeListener(view: TextInputEditText, listener: View.OnFocusChangeListener) {
//    view.onFocusChangeListener = listener
//}

@BindingAdapter("errorText")
fun setErrorMessage(view: TextInputLayout, errorMessage: String?) {
    view.error = errorMessage
}