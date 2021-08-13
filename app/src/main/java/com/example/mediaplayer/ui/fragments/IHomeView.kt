package com.example.mediaplayer.ui.fragments

import com.example.mediaplayer.model.Status
import com.example.mediaplayer.model.response.Data
import kotlinx.coroutines.flow.Flow

interface IHomeView{

    fun onGetFilms(flow: Flow<Status<Data>>)

}