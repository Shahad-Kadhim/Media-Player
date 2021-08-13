package com.example.mediaplayer.model.network

import com.example.mediaplayer.model.Status
import okhttp3.*

class Client(private val converter: IConverter) :IClient{

    private val baseUrl = "https://raw.githubusercontent.com/Bareq-altaamah/mock/main/classic.json"
    private val okHttpClient = OkHttpClient()

    override fun getResponse()=
        okHttpClient.newCall(Request.Builder().url(baseUrl).build()).execute()

    override fun getFilmsFromApi()=
        checkResponse(getResponse())



    fun checkResponse(response: Response) =
        response.run {
            if (isSuccessful){
                val films = converter.convert(body!!.string())
                films.feed[0].items
                Status.Success(films)
            } else {
                Status.Error
            }
        }

}
