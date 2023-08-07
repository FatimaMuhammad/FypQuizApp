package com.example.fypfsy.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.VideoView
import com.example.fypfsy.R

class VideoViews : AppCompatActivity() {
    private val videoMap = hashMapOf(
        "7-7-23" to "https://www.example.com/video1.mp4",
        "8-7-23" to "https://www.example.com/video2.mp4",
        "9-7-23" to "https://www.example.com/video3.mp4",
        "10-7-23" to "https://www.example.com/video4.mp4",
        "11-7-23" to "https://www.example.com/video5.mp4",
        // Add more video titles and URLs here
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_views)

        val videoTitle = intent.getStringExtra("videoTitle")
        val videoUrl = videoMap[videoTitle]
        if (videoUrl != null) {
            val videoView: VideoView = findViewById(R.id.videoView)
            videoView.setVideoURI(Uri.parse(videoUrl))
            videoView.start()

            val skipButton: Button = findViewById(R.id.skipButton)
            skipButton.setOnClickListener {
                openQuestionActivity()
            }

            val goToQuizButton: Button = findViewById(R.id.goToQuizButton)
            goToQuizButton.setOnClickListener {
                openQuestionActivity()
            }
        } else {
            // Handle the case when the video URL is not found
            // Display an error message or take appropriate action
            finish()
        }
    }

    private fun openQuestionActivity() {
        val intent = Intent(this, QuestionActivity::class.java)
        startActivity(intent)
    }
}