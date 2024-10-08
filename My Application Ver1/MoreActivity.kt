package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more)

        val navHome = findViewById<LinearLayout>(R.id.nav_home)
        val navNews = findViewById<LinearLayout>(R.id.nav_news)
        val navRace = findViewById<LinearLayout>(R.id.nav_race)
        val navHistory = findViewById<LinearLayout>(R.id.nav_history)
        val navMore = findViewById<LinearLayout>(R.id.nav_more)

        navHome.setOnClickListener {
            changeColorAndNavigateWithDelay(navHome, SearchResultActivity::class.java)
        }

        navNews.setOnClickListener {
            changeColorAndNavigateWithDelay(navNews, NewsActivity::class.java)
        }

        navRace.setOnClickListener {
            changeColorAndNavigateWithDelay(navRace, YouTubeVideosActivity::class.java)
        }

        navHistory.setOnClickListener {
            changeColorAndNavigateWithDelay(navHistory, HistoryActivity::class.java)
        }

        navMore.setOnClickListener {
            changeColorAndNavigateWithDelay(navMore, MoreActivity::class.java)
        }

        val linkCalculator = findViewById<TextView>(R.id.link_calculator)
        val linkSettings = findViewById<TextView>(R.id.link_settings)
        val linkSocialWall = findViewById<TextView>(R.id.link_social_wall)
        val linkPhoto = findViewById<TextView>(R.id.link_photo)
        val linkAuthors = findViewById<TextView>(R.id.link_authors)
        val linkQuiz = findViewById<TextView>(R.id.link_quiz)
        val lingMemoryGame = findViewById<TextView>(R.id.link_memory_game)
        val linkLoopGame = findViewById<TextView>(R.id.link_loop_game)

        linkAuthors.setOnClickListener {
            startActivity(Intent(this, AuthorsActivity::class.java))
        }

        linkCalculator.setOnClickListener {
            startActivity(Intent(this, GrandPrixCalculatorActivity::class.java))
        }

        linkSocialWall.setOnClickListener {
            startActivity(Intent(this, GreenBackgroundActivity::class.java))
        }

        linkPhoto.setOnClickListener {
            startActivity(Intent(this, PhotoGalleryActivity::class.java))
        }

        linkQuiz.setOnClickListener {
            startActivity(Intent(this, QuizActivity::class.java))
        }

        lingMemoryGame.setOnClickListener {
            startActivity(Intent(this, QuizGameActivity::class.java))
        }

        linkSettings.setOnClickListener {
            startActivity(Intent(this, GreenBackgroundActivity::class.java))
        }

        linkLoopGame.setOnClickListener {
            startActivity(Intent(this, LoopGameActivity::class.java))
        }

        applyAnimation(linkAuthors, 200)
        applyAnimation(linkCalculator, 300)
        applyAnimation(linkSocialWall, 400)
        applyAnimation(linkPhoto, 500)
        applyAnimation(linkQuiz, 600)
        applyAnimation(lingMemoryGame, 700)
        applyAnimation(linkLoopGame, 750)
        applyAnimation(linkSettings, 800)

    }

    private fun applyAnimation(view: View, delay: Long) {
        view.alpha = 0f
        view.translationY = 50f
        view.animate()
            .alpha(1f)
            .translationY(0f)
            .setInterpolator(DecelerateInterpolator())
            .setStartDelay(delay)
            .setDuration(500)
            .start()
    }

    private fun changeColorAndNavigateWithDelay(layout: LinearLayout, activityClass: Class<*>) {
        val textViewMap = mapOf(
            R.id.nav_home to R.id.nav_a,
            R.id.nav_news to R.id.nav_b,
            R.id.nav_race to R.id.nav_c,
            R.id.nav_history to R.id.nav_d,
            R.id.nav_more to R.id.nav_e
        )

        for ((parentId, textViewId) in textViewMap) {
            findViewById<TextView>(textViewId)?.setTextColor(ContextCompat.getColor(this, android.R.color.white))
        }

        val selectedTextViewId = textViewMap[layout.id]
        findViewById<TextView>(selectedTextViewId!!)?.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_light))

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, activityClass))

            findViewById<TextView>(selectedTextViewId)?.setTextColor(ContextCompat.getColor(this, android.R.color.white))
        }, 300)
    }
}
