package rodolfogusson.testepicpay.core.ui

import androidx.databinding.BindingAdapter
import android.widget.ImageView
import com.squareup.picasso.Picasso

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    Picasso
        .get()
        .load(url)
        .into(imageView)
}