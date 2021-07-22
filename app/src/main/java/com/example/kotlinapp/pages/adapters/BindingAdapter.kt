package com.example.kotlinapp.pages.adapters

import android.graphics.Typeface
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import coil.request.ImageRequest
import coil.size.ViewSizeResolver
import timber.log.Timber

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    Timber.d("%s - bindIsGone: %s", view.id, isGone)
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("isVisible")
fun bindIsVisible(view: View, isVisible: Boolean) {
    Timber.d("%s - bindIsVisible: %s", view.id,isVisible)
    view.visibility = if (isVisible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("isInVisible")
fun bindIsInVisible(view: View, isInVisible: Boolean) {
    view.visibility = if (isInVisible) {
        View.INVISIBLE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter(value = ["imageFromUrl", "imageRequestListener"], requireAll = false)
fun bindImageFromUrl(view: ImageView, imageUrl: String?, listener: ImageRequest.Listener?) {
    if (!imageUrl.isNullOrEmpty()) {
        view.load(imageUrl) {
            crossfade(true)
            size(ViewSizeResolver(view))
            listener(listener)
        }
    }
}

@BindingAdapter("labelStyle")
fun bindTextStyle(view: TextView, style: Int) {
    view.typeface = Typeface.defaultFromStyle(style)
}