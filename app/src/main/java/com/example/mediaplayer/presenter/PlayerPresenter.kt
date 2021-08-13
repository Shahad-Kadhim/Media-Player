package com.example.mediaplayer.presenter

import com.example.mediaplayer.model.repository.Repository
import com.example.mediaplayer.ui.fragments.IPlayerView

class PlayerPresenter(private val view: IPlayerView) {

    fun getMediaItem(url:String) {
        view.onGetMediaItem(Repository.getMedia(url))
    }

}