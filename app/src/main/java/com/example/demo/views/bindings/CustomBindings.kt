package com.example.demo.views.bindings

import android.net.Uri
import android.view.View
import androidx.databinding.BindingAdapter
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.request.ImageRequest

class CustomBindings {

    companion object {

        @BindingAdapter("url")
        @JvmStatic
        fun bindSimpleDraweeView(view: SimpleDraweeView, url: String) {

            val builder = Fresco.newDraweeControllerBuilder()
                .setImageRequest(ImageRequest.fromUri(Uri.parse(url)))
                .setOldController(view.controller)

            view.controller = builder.build()
        }
    }
}

@BindingAdapter("android:visibility")
fun View.setVisibility(value: Boolean?) {
    visibility = if (value != null && value) View.VISIBLE else View.GONE
}
