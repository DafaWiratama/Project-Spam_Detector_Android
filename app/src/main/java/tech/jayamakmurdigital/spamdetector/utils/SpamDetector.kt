package tech.jayamakmurdigital.spamdetector.utils

import android.content.Context
import android.util.Log
import androidx.lifecycle.asLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class SpamDetector(context: Context) {
    private val queue: RequestQueue = Volley.newRequestQueue(context)

    fun checkMessage(text: String) = callbackFlow {
        val stringRequest = StringRequest(Request.Method.GET, "http://24.24.24.11:8080/?text=${text}", { response ->
            CoroutineScope(Dispatchers.IO).launch { if (response != "") offer(response.toInt()) else offer(0) }
        }, { Log.e("Volley", it.message.toString()) })
        queue.add(stringRequest)
        awaitClose {}
    }.asLiveData(Dispatchers.IO)
}