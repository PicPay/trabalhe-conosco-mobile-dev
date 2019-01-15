package rodolfogusson.testepicpay.core.ui

import androidx.databinding.BindingAdapter
import android.widget.ImageView
import com.squareup.picasso.Picasso

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