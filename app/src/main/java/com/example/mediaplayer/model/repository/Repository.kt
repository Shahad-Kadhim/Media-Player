package com.example.mediaplayer.model.repository

import com.example.mediaplayer.model.Status
import com.example.mediaplayer.model.network.Client
import com.example.mediaplayer.model.network.IConverter
import com.example.mediaplayer.model.network.VideoPlayer
import com.example.mediaplayer.model.response.Data
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object Repository :IConverter{

    private val client=Client(this)

    override fun convert(body: String)= Gson().fromJson(body,Data::class.java)!!

    fun getFilms()=
        flow {
            emit(Status.Loading)
            emit(client.getFilmsFromApi())
        }.flowOn(Dispatchers.IO)

    fun getMedia(url:String)=
        VideoPlayer.getMedia(url)

}