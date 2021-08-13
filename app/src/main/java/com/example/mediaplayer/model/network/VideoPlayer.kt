package com.example.mediaplayer.model.network

import com.google.android.exoplayer2.MediaItem

object  VideoPlayer {

    fun getMedia(url:String)=
        MediaItem.fromUri(url)

}