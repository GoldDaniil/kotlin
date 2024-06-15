package com.example.myapplication

import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class ReactionGameActivity : AppCompatActivity() {

    private lateinit var light1: ImageView
    private lateinit var light2: ImageView
    private lateinit var light3: ImageView
    private lateinit var light4: ImageView
    private lateinit var light5: ImageView
    private lateinit var reactionTimeText: TextView
    private lateinit var startButton: Button
    private lateinit var rootLayout: LinearLayout

    private var startTime: Long = 0L
    private var reactionTime: Long = 0L
    private var isGameStarted = false
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reaction_game)

        light1 = findViewById(R.id.light_1)
        light2 = findViewById(R.id.light_2)
        light3 = findViewById(R.id.light_3)
        light4 = findViewById(R.id.light_4)
        light5 = findViewById(R.id.light_5)
        reactionTimeText = findViewById(R.id.reaction_time_text)
        startButton = findViewById(R.id.start_button)
        rootLayout = findViewById(R.id.root_layout)

        startButton.setOnClickListener {
            startGame()
        }

        rootLayout.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                if (isGameStarted) {
                    recordReactionTime()
                }
            }
            true
        }
    }

    private fun startGame() {
        startButton.visibility = View.GONE
        reactionTimeText.visibility = View.GONE
        resetLights()

        handler.postDelayed({
            light1.setBackgroundResource(R.drawable.red_light_on)
        }, 1000)

        handler.postDelayed({
            light2.setBackgroundResource(R.drawable.red_light_on)
        }, 2000)

        handler.postDelayed({
            light3.setBackgroundResource(R.drawable.red_light_on)
        }, 3000)

        handler.postDelayed({
            light4.setBackgroundResource(R.drawable.red_light_on)
        }, 4000)

        handler.postDelayed({
            light5.setBackgroundResource(R.drawable.red_light_on)
        }, 5000)

        val randomDelay = 5000 + Random.nextLong(1000, 5000)

        handler.postDelayed({
            resetLights()
            startTime = SystemClock.elapsedRealtime()
            isGameStarted = true
        }, randomDelay)
    }

    private fun resetLights() {
        light1.setBackgroundResource(R.drawable.red_light_off)
        light2.setBackgroundResource(R.drawable.red_light_off)
        light3.setBackgroundResource(R.drawable.red_light_off)
        light4.setBackgroundResource(R.drawable.red_light_off)
        light5.setBackgroundResource(R.drawable.red_light_off)
    }

    private fun recordReactionTime() {
        reactionTime = SystemClock.elapsedRealtime() - startTime
        isGameStarted = false
        reactionTimeText.visibility = View.VISIBLE
        reactionTimeText.text = "Your reaction time: ${reactionTime} ms"
        startButton.visibility = View.VISIBLE
    }
}
