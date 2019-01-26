package rodolfogusson.testepicpay.core.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso
import java.math.BigDecimal

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    Picasso
        .get()
        .load(url)
        .into(imageView)
}

@BindingAdapter("errorText")
fun setErrorMessage(view: TextInputLayout, errorMessage: String?) {
    view.error = errorMessage
}

@BindingAdapter("decimalValue", "decimalTextFormat", requireAll = true)
fun setDecimalText(view: TextView, decimalValue: BigDecimal?, decimalTextFormat: String) {
    view.text = String.format(decimalTextFormat, decimalValue).replace(".", ",")
}