package com.example.myapplication

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoopGameActivity : AppCompatActivity() {

    private lateinit var helmetIcon: ImageView
    private lateinit var colorZone: View
    private lateinit var scoreTextView: TextView
    private lateinit var retryButton: Button

    private var score = 0
    private var gameRunning = true
    private var animationDuration = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loop_game)

        helmetIcon = findViewById(R.id.helmetIcon)
        colorZone = findViewById(R.id.colorZone)
        scoreTextView = findViewById(R.id.scoreTextView)
        retryButton = findViewById(R.id.retryButton)

        helmetIcon.setOnClickListener {
            checkIfInZone()
        }

        retryButton.setOnClickListener {
            resetGame()
        }

        startGame()
    }

    private fun startGame() {
        gameRunning = true
        moveHelmet()
    }

    private fun moveHelmet() {
        if (!gameRunning) return

        val screenWidth = resources.displayMetrics.widthPixels
        val startPosition = 0f
        val endPosition = (screenWidth - helmetIcon.width).toFloat()

        val animator = ObjectAnimator.ofFloat(helmetIcon, "translationX", startPosition, endPosition).apply {
            duration = animationDuration
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
            start()
        }

        helmetIcon.setOnClickListener {
            animator.cancel()
            checkIfInZone()
        }
    }

    private fun checkIfInZone() {
        val helmetX = helmetIcon.x
        val colorZoneX = colorZone.x
        val colorZoneWidth = colorZone.width

        if (helmetX >= colorZoneX && helmetX <= colorZoneX + colorZoneWidth) {
            score++
            scoreTextView.text = "Score: $score"
            animationDuration -= 200
            moveHelmet()
        } else {
            endGame()
        }
    }

    private fun endGame() {
        gameRunning = false
        helmetIcon.visibility = View.GONE
        retryButton.visibility = View.VISIBLE
    }

    private fun resetGame() {
        score = 0
        scoreTextView.text = "Score: $score"
        animationDuration = 3000L
        helmetIcon.visibility = View.VISIBLE
        retryButton.visibility = View.GONE
        startGame()
    }
}
