package com.example.mediaplayer.model.network

import com.example.mediaplayer.model.response.Data
import com.example.mediaplayer.model.Status
import okhttp3.Response

interface IClient {

   fun getResponse():Response

   fun getFilmsFromApi() : Status<Data>

}