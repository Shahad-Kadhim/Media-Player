package com.example.mediaplayer.ui.fragments

import android.view.LayoutInflater
import com.example.mediaplayer.databinding.FragmentPlayerBinding
import com.example.mediaplayer.presenter.PlayerPresenter
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer

class PLayerFragment : BaseFragment<FragmentPlayerBinding>(),IPlayerView {
    override val TAG ="PLAYER_Fragment"
    override val bindingInflater: (LayoutInflater) -> FragmentPlayerBinding=FragmentPlayerBinding::inflate
    var playbackPosition = 0L
    val presenter=PlayerPresenter(this)

    private fun initialPlayer(){
        arguments?.let {
            presenter.getMediaItem(it.getString("VideoURL")!!)
        }
    }

    override fun onGetMediaItem(mediaItem: MediaItem) {
        activity?.applicationContext?.let { context ->
            SimpleExoPlayer.Builder(context).build().also { exoPlayer ->
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.playWhenReady = true
                exoPlayer.seekTo(playbackPosition)
                exoPlayer.prepare()
                binding?.videoView?.player = exoPlayer
            }
        }
    }

    private fun releasePlayer() {
        binding?.videoView?.player?.let {
            playbackPosition = it.currentPosition
            it.playWhenReady=false
            it.stop()
            it.pause()
        }
    }
    
    override fun onResume() {
        super.onResume()
        initialPlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun setup() {}
    override fun callBack() {}

}