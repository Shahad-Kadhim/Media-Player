package com.example.mediaplayer.ui

import android.view.LayoutInflater
import com.example.mediaplayer.databinding.ActivityPlayerBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer

class PLayer :BaseActivity<ActivityPlayerBinding>() {
    override val TAG ="PLAYER_ACTIVITY"
    override val inflater: (LayoutInflater) -> ActivityPlayerBinding=ActivityPlayerBinding::inflate
    var playbackPosition = 0L

    private fun initialPlayer(){
        intent.getStringExtra("VideoURL")?.let {
            SimpleExoPlayer.Builder(this).build().also { exoPlayer ->
                exoPlayer.setMediaItem(MediaItem.fromUri(it))
                binding.videoView.player = exoPlayer
                exoPlayer.playWhenReady = true
                exoPlayer.seekTo(playbackPosition)
                exoPlayer.prepare()
            }
        }
    }

    private fun releasePlayer() {
        binding.videoView.player?.let {
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
    override fun callback() {}


}