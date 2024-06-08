package com.example.myapplication

import android.os.Handler
import android.os.Looper
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class SearchResultActivity : AppCompatActivity() {

    private val delayMillis: Long = 80 // 60/1000 секунды

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        val imageView1 = findViewById<ImageView>(R.id.imageView1)
        val overlayView1 = findViewById<View>(R.id.overlayView1)
        val textView1 = findViewById<TextView>(R.id.textView1)
        val button1 = findViewById<Button>(R.id.button1)

        val imageView2 = findViewById<ImageView>(R.id.imageView2)
        val overlayView2 = findViewById<View>(R.id.overlayView2)
        val textView2 = findViewById<TextView>(R.id.textView2)
        val button2 = findViewById<Button>(R.id.button2)

        val imageView3 = findViewById<ImageView>(R.id.imageView3)
        val overlayView3 = findViewById<View>(R.id.overlayView3)
        val textView3 = findViewById<TextView>(R.id.textView3)
        val button3 = findViewById<Button>(R.id.button3)

        val imageView4 = findViewById<ImageView>(R.id.imageView4)
        val overlayView4 = findViewById<View>(R.id.overlayView4)
        val textView4 = findViewById<TextView>(R.id.textView4)
        val button4 = findViewById<Button>(R.id.button4)

        val imageView5 = findViewById<ImageView>(R.id.imageView5)
        val overlayView5 = findViewById<View>(R.id.overlayView5)
        val textView5 = findViewById<TextView>(R.id.textView5)
        val button5 = findViewById<Button>(R.id.button5)

        val textViews = listOf(textView1, textView2, textView3, textView4, textView5)
        val overlayViews = listOf(overlayView1, overlayView2, overlayView3, overlayView4, overlayView5)
        val buttons = listOf(button1, button2, button3, button4, button5)

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


        imageView1.setOnClickListener {
            toggleTextViewVisibility(textView1, overlayView1, button1, textViews, overlayViews, buttons)
        }

        imageView2.setOnClickListener {
            toggleTextViewVisibility(textView2, overlayView2, button2, textViews, overlayViews, buttons)
        }

        imageView3.setOnClickListener {
            toggleTextViewVisibility(textView3, overlayView3, button3, textViews, overlayViews, buttons)
        }

        imageView4.setOnClickListener {
            toggleTextViewVisibility(textView4, overlayView4, button4, textViews, overlayViews, buttons)
        }

        imageView5.setOnClickListener {
            toggleTextViewVisibility(textView5, overlayView5, button5, textViews, overlayViews, buttons)
        }

        button1.setOnClickListener {
            val intent = Intent(this, NewsActivity::class.java)
            startActivity(intent)
        }

        button2.setOnClickListener {
            val intent = Intent(this, YouTubeVideosActivity::class.java)
            startActivity(intent)
        }

        button3.setOnClickListener {
            val intent = Intent(this, GrandPrixCalculatorActivity::class.java)
            startActivity(intent)
        }

        button4.setOnClickListener {
            val intent = Intent(this, PhotoGalleryActivity::class.java)
            startActivity(intent)
        }

        button5.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            startActivity(intent)
        }
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
        startActivity(Intent(this, QuizActivity::class.java))
    }

    private fun toggleTextViewVisibility(textView: TextView, overlayView: View, button: Button?, allTextViews: List<TextView>, allOverlayViews: List<View>, allButtons: List<Button>) {
        for (i in allTextViews.indices) {
            if (allTextViews[i].visibility == View.VISIBLE && allTextViews[i] != textView) {
                allTextViews[i].animate().alpha(0f).setDuration(300).withEndAction {
                    allTextViews[i].visibility = View.GONE
                }
                allOverlayViews[i].animate().alpha(0f).setDuration(300).withEndAction {
                    allOverlayViews[i].visibility = View.GONE
                }
                allButtons[i].visibility = View.GONE
            }
        }

        if (textView.visibility == View.GONE) {
            textView.alpha = 0f
            textView.visibility = View.VISIBLE
            textView.animate().alpha(1f).setDuration(300)

            overlayView.alpha = 0f
            overlayView.visibility = View.VISIBLE
            overlayView.animate().alpha(1f).setDuration(300)

            button?.visibility = View.VISIBLE
        } else {
            textView.animate().alpha(0f).setDuration(300).withEndAction {
                textView.visibility = View.GONE
            }
            overlayView.animate().alpha(0f).setDuration(300).withEndAction {
                overlayView.visibility = View.GONE
            }

            button?.visibility = View.GONE
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
