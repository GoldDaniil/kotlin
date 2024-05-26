package com.example.myapplication

import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class FullScreenVideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_video)

        val videoId = intent.getIntExtra("videoId", -1)
        if (videoId != -1) {
            val videoView = findViewById<VideoView>(R.id.videoView)
            val mediaController = MediaController(this)
            mediaController.setAnchorView(videoView)
            videoView.setMediaController(mediaController)

            val videoPath = "android.resource://" + packageName + "/" + videoId
            val videoUri = Uri.parse(videoPath)

            videoView.setVideoURI(videoUri)
            videoView.start()
        } else {
            // обработка ошибки: если videoId не был передан
        }
    }
}
