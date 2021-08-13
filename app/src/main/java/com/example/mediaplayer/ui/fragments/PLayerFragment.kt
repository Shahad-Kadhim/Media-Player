package com.example.mediaplayer.ui.fragments

import android.app.AlertDialog
import android.view.LayoutInflater
import com.example.mediaplayer.databinding.FragmentPlayerBinding
import com.example.mediaplayer.model.response.Item
import com.example.mediaplayer.presenter.PlayerPresenter
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer

class PLayerFragment : BaseFragment<FragmentPlayerBinding>(),IPlayerView {

    override val TAG ="PLAYER_Fragment"
    override val bindingInflater: (LayoutInflater) -> FragmentPlayerBinding=FragmentPlayerBinding::inflate
    private var playbackPosition = 0L
    private val presenter=PlayerPresenter(this)
    private lateinit var currnetFilm:Item


    override fun callBack() {
        binding?.info?.setOnClickListener {
            AlertDialog.Builder(activity)
                .setTitle(currnetFilm.title)
                .setMessage("${ currnetFilm.description }.\n\n Directed by : ${currnetFilm.director}")
                .create()
                .show()
        }
    }


    private fun initialPlayer(){
        arguments?.let {
            currnetFilm= it.getParcelable("VideoURL")!!
            presenter.getMediaItem(currnetFilm.url)
        }
    }

    override fun onGetMediaItem(mediaItem: MediaItem) {
        activity?.applicationContext?.let { context ->
            SimpleExoPlayer.Builder(context).build().also { exoPlayer ->
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.seekTo(playbackPosition)
                binding?.videoView?.player = exoPlayer
                exoPlayer.playWhenReady = true
                exoPlayer.prepare()
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

}