package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more)

        // Панель навигации
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

        findViewById<TextView>(R.id.link_calculator).setOnClickListener {
            startActivity(Intent(this, GrandPrixCalculatorActivity::class.java))
        }

        findViewById<TextView>(R.id.link_settings).setOnClickListener {
            startActivity(Intent(this, GreenBackgroundActivity::class.java))
        }

        findViewById<TextView>(R.id.link_social_wall).setOnClickListener {
            startActivity(Intent(this, GreenBackgroundActivity::class.java))
        }

        findViewById<TextView>(R.id.link_photo).setOnClickListener {
            startActivity(Intent(this, PhotoGalleryActivity::class.java))
        }

        fun openSearchResultActivity(view: View) {
            startActivity(Intent(this, SearchResultActivity::class.java))
        }

        fun openNewsActivity(view: View) {
            startActivity(Intent(this, NewsActivity::class.java))
        }

        fun openYouTubeVideosActivity(view: View) {
            startActivity(Intent(this, YouTubeVideosActivity::class.java))
        }

        fun openGreenBackgroundActivity(view: View) {
            startActivity(Intent(this, HistoryActivity::class.java))
        }

        fun openMoreActivity(view: View) {
            startActivity(Intent(this, MoreActivity::class.java))
        }
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
