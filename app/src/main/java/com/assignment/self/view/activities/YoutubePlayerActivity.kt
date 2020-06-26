package com.assignment.self.view.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.assignment.self.R
import com.assignment.self.databinding.ActivityYoutubePlayerBinding
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer

class YoutubePlayerActivity : YouTubeBaseActivity() , YouTubePlayer.OnInitializedListener {
    var key :String ?= null
    lateinit var binding : ActivityYoutubePlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_youtube_player)
        key = intent.getStringExtra("youtube_video_key")
        binding.youtubePlayerView.initialize("AIzaSyDiytGDtJFYJbYShnLQV1Yi-F4z6mbvBQY",this)
    }

    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        player: YouTubePlayer?,
        p2: Boolean
    ) {
      player!!.cueVideo(key)
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {

    }
}