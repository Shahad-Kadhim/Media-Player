package com.example.mediaplayer.ui.fragments

import com.google.android.exoplayer2.MediaItem

interface IPlayerView {
    fun onGetMediaItem(mediaItem: MediaItem)
}