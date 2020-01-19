package com.pencelab.darkskyweather.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.pencelab.darkskyweather.R
import com.pencelab.darkskyweather.utils.setRemoteImageResource
import okhttp3.*

@BindingAdapter("icon")
fun bindIconUrl(view: ImageView, icon: String?) {
    if (!icon.isNullOrEmpty()) {
        val resourceId = view.context!!.resources.getIdentifier(icon.replace('-', '_'), "string", view.context!!.packageName)
        val iconUrl = view.context.getString(resourceId)
        val onFailure = { view.setImageResource(R.drawable.ic_error) }
        val request: Request = Request.Builder().url(iconUrl).build()
        view.setRemoteImageResource(request, onFailure)
    } else {
        val iconUrl = view.context.getString(R.string.def)
        val onFailure = { view.setImageResource(R.drawable.ic_weather_question) }
        val request: Request = Request.Builder().url(iconUrl).build()
        view.setRemoteImageResource(request, onFailure)
    }

}
