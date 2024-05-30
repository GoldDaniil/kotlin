package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class YouTubeVideosActivity : AppCompatActivity() {

    private val delayMillis: Long = 65 // 60/1000 секунды

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube_videos)

        val countrySpinner = findViewById<Spinner>(R.id.countrySpinner)
        val watchButton = findViewById<Button>(R.id.watchButton)

        val countries = arrayOf("Бахрейн", "Австралия", "Монако", "Саудовская Аравия",
            "Азербайджан", "Испания", "Италия", "Япония", "Эмилии-Романьи", "Австрия",
            "Великобритания"
        )

        val adapter = ArrayAdapter(this, R.layout.spinner_item, countries)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        countrySpinner.adapter = adapter

        countrySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedCountry = parent.getItemAtPosition(position).toString()
                val videoId = when (selectedCountry) {
                    "Бахрейн" -> R.raw.video_grand_prix_bahrain
                    "Австралия" -> R.raw.video_grand_prix_australia
                    "Монако" -> R.raw.video_grand_prix_monako
                    "Саудовская Аравия" -> R.raw.video_grand_prix_saudia_aravia
                    "Азербайджан" -> R.raw.video_grand_prix_azer
                    "Испания", "Италия", "Япония", "Австрия" -> R.raw.video_grand_prix_italy
                    "Эмилии-Романьи" -> R.raw.video_grand_prix_emiliiromanii
                    "Великобритания" -> R.raw.video_grand_prix_emiliiromanii
                    else -> R.raw.video_grand_prix_italy
                }
                watchButton.setOnClickListener {
                    val intent = Intent(this@YouTubeVideosActivity, FullScreenVideoActivity::class.java)
                    intent.putExtra("videoId", videoId)
                    startActivity(intent)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // обработка - если ничего не выбрано
            }
        }

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
            
        }

        navHistory.setOnClickListener {
            changeColorAndNavigateWithDelay(navHistory, GreenBackgroundActivity::class.java)
        }

        navMore.setOnClickListener {
            changeColorAndNavigateWithDelay(navMore, GreenBackgroundActivity::class.java)
        }

        watchButton.setOnClickListener {
            
        }
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
