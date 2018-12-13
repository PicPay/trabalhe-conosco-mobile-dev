package test.edney.picpay.view

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import test.edney.picpay.R

object CustomSetter {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, url: String) {
        Glide.with(view.context)
            .load(url)
            .apply(RequestOptions.circleCropTransform().placeholder(R.drawable.placeholder))
            .into(view)
    }
}