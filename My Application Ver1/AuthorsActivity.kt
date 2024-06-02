package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class AuthorsActivity : AppCompatActivity() {

    private val delayMillis: Long = 65 // 60/1000 секунды

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authors)

        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val imageView1 = findViewById<ImageView>(R.id.imageView1)
        val imageView2 = findViewById<ImageView>(R.id.imageView2)

        val titleAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        titleTextView.startAnimation(titleAnimation)

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

        val imageAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in_delayed)
        imageAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                imageView1.visibility = View.VISIBLE
                imageView1.startAnimation(AnimationUtils.loadAnimation(this@AuthorsActivity, R.anim.fade_in_delayed))
            }
        })
        imageView2.visibility = View.VISIBLE
        imageView2.startAnimation(imageAnimation)
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

    fun changeColorAndNavigateWithDelay(layout: LinearLayout, activityClass: Class<*>) {
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
        }, delayMillis)
    }
}
