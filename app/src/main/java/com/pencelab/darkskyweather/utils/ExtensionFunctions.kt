package com.pencelab.darkskyweather.utils

import android.widget.ImageView
import com.pixplicity.sharp.Sharp
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.io.InputStream

fun ImageView.setRemoteImageResource(request: Request, onFailure: () -> Unit) {
    HttpClient.newCall(request).enqueue(object : Callback {
        override fun onResponse(call: Call, response: Response) {
            try {
                val stream: InputStream = response.body()!!.byteStream()
                Sharp.loadInputStream(stream).into(this@setRemoteImageResource)
                stream.close()
            } catch (e: Exception) {
                onFailure()
            }
        }

        override fun onFailure(call: Call, e: IOException) {
            onFailure()
        }
    })
}
