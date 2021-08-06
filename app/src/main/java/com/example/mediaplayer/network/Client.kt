package com.example.mediaplayer.network

import com.example.mediaplayer.response.data
import com.example.mediaplayer.util.Status
import com.google.gson.Gson
import okhttp3.*

object Client {
    private val okHttpClient = OkHttpClient()
    private val baseUrl = "https://raw.githubusercontent.com/Bareq-altaamah/mock/main/classic.json"
    private val gson = Gson()
    fun getFilms():Status<data> {
        okHttpClient.newCall(Request.Builder().url(baseUrl).build()).execute().apply {
            return if (isSuccessful){
                val films = gson.fromJson(body!!.string(), data::class.java)
                films.feed[0].items
                Status.Success(films)
            } else {
                Status.Error
            }
        }
    }
}
