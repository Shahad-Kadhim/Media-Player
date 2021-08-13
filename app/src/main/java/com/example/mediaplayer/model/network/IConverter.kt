package com.example.mediaplayer.model.network

import com.example.mediaplayer.model.response.Data

interface IConverter {

    fun convert(body:String):Data

}
