package edu.rit.connors.brandon.mtgandroidscryfallsearch.util

import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.*

class HttpRequestQueue constructor(context: Context) {
    companion object {
        @Volatile
        private var INSTANCE: HttpRequestQueue? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: HttpRequestQueue(context).also {
                    INSTANCE = it
                }
            }
    }
    val imageLoader: ImageLoader by lazy {
        ImageLoader(requestQueue,
            object : ImageLoader.ImageCache {
                private val cache = LruCache<String, Bitmap>(20)
                override fun getBitmap(url: String): Bitmap? {
                    return null
                }
                override fun putBitmap(url: String, bitmap: Bitmap) {

                }
            })
    }
    val requestQueue: RequestQueue by lazy {
        // applicationContext is key, it keeps you from leaking the
        // Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }
    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }
}