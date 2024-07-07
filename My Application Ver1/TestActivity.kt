package com.example.myapplication

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2

class TestActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private val delayMillis: Long = 80 // 60/1000 секунды

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = ViewPager2()

        setupTopNavigation()
        setupBottomNavigation()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateTopNavigation(position)
            }
        })
    }

    private fun setupTopNavigation() {
        val icon1 = findViewById<ImageView>(R.id.icon_1)
        val icon2 = findViewById<ImageView>(R.id.icon_2)
        val icon3 = findViewById<ImageView>(R.id.icon_3)
        val icon4 = findViewById<ImageView>(R.id.icon_4)
        val icon5 = findViewById<ImageView>(R.id.icon_5)

        icon1.setOnClickListener { viewPager.setCurrentItem(0, true) }
        icon2.setOnClickListener { viewPager.setCurrentItem(1, true) }
        icon3.setOnClickListener { viewPager.setCurrentItem(2, true) }
        icon4.setOnClickListener { viewPager.setCurrentItem(0, true) }
        icon5.setOnClickListener { viewPager.setCurrentItem(1, true) }
    }

    private fun setupBottomNavigation() {
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
    }

    private fun updateTopNavigation(position: Int) {
        val icon1 = findViewById<ImageView>(R.id.icon_1)
        val icon2 = findViewById<ImageView>(R.id.icon_2)
        val icon3 = findViewById<ImageView>(R.id.icon_3)
        val icon4 = findViewById<ImageView>(R.id.icon_4)
        val icon5 = findViewById<ImageView>(R.id.icon_5)

        val defaultColor = ContextCompat.getColor(this, android.R.color.white)
        val selectedColor = ContextCompat.getColor(this, android.R.color.holo_red_light)

        icon1.setColorFilter(defaultColor)
        icon2.setColorFilter(defaultColor)
        icon3.setColorFilter(defaultColor)
        icon4.setColorFilter(defaultColor)
        icon5.setColorFilter(defaultColor)

        when (position) {
            0 -> {
                icon1.setColorFilter(selectedColor)
                icon4.setColorFilter(selectedColor)
            }
            1 -> {
                icon2.setColorFilter(selectedColor)
                icon5.setColorFilter(selectedColor)
            }
            2 -> {
                icon3.setColorFilter(selectedColor)
            }
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
        }, delayMillis)
    }
}
