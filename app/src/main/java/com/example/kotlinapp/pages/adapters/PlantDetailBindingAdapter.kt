package com.example.kotlinapp.pages.adapters

import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.example.kotlinapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

@BindingAdapter("isFabGone")
fun bindIsFabGone(view: FloatingActionButton, isGone: Boolean?) {
    if (isGone == null || isGone) {
        view.hide()
    } else {
        view.show()
    }
}

@BindingAdapter("renderHtml")
fun bindRenderHtml(view: TextView, description: String?) {
    if (description != null) {
        view.text = HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT)
        view.movementMethod = LinkMovementMethod.getInstance()
    } else {
        view.text = ""
    }
}

@BindingAdapter("wateringText")
fun bindWateringText(textView: TextView, wateringInterval: Int) {
    val resources = textView.context.resources
    val quantityString = resources.getQuantityString(
        R.plurals.watering_needs_suffix,
        wateringInterval,
        wateringInterval
    )

    textView.text = quantityString
}