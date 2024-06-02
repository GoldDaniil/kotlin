package com.example.myapplication

import android.widget.LinearLayout
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

class PhotoGalleryActivity : AppCompatActivity() {
    
    private val delayMillis: Long = 65 // 60/1000 секунды

    private val imagesTop = arrayOf(
        R.drawable.photo4, R.drawable.photo2, R.drawable.photo3, R.drawable.photo1,
        R.drawable.photo5, R.drawable.photo6, R.drawable.photo7, R.drawable.photo8,
        R.drawable.photo9, R.drawable.photo10, R.drawable.photo11, R.drawable.photo12
    )
    private val imagesSecond = arrayOf(
        R.drawable.tech10, R.drawable.tech1, R.drawable.tech2, R.drawable.tech3, R.drawable.tech4,
        R.drawable.tech5, R.drawable.tech6, R.drawable.tech7, R.drawable.tech8, R.drawable.tech9
    )
    private val imagesThird = arrayOf(
        R.drawable.legends1, R.drawable.legends2, R.drawable.legends3, R.drawable.legends4,
        R.drawable.legends5, R.drawable.legends6
    )
    private val imagesBottom = arrayOf(
        R.drawable.crash1, R.drawable.crash2, R.drawable.crash3, R.drawable.crash4, R.drawable.crash5,
        R.drawable.crash6, R.drawable.crash7, R.drawable.crash8
    )

    private val textsTop = arrayOf(
        "aesthetics"
    )
    private val textsSecond = arrayOf(
        "technique"
    )
    private val textsThird = arrayOf(
        "legends"
    )
    private val textsBottom = arrayOf(
        "crash"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_gallery)

        val viewPagerTop = findViewById<ViewPager>(R.id.viewPagerTop)
        val viewPagerSecond = findViewById<ViewPager>(R.id.viewPagerSecond)
        val viewPagerThird = findViewById<ViewPager>(R.id.viewPagerThird)
        val viewPagerBottom = findViewById<ViewPager>(R.id.viewPagerBottom)

        val adapterTop = PhotoPagerAdapter(this, imagesTop, textsTop)
        val adapterSecond = PhotoPagerAdapter(this, imagesSecond, textsSecond)
        val adapterThird = PhotoPagerAdapter(this, imagesThird, textsThird)
        val adapterBottom = PhotoPagerAdapter(this, imagesBottom, textsBottom)

        viewPagerTop.adapter = adapterTop
        viewPagerSecond.adapter = adapterSecond
        viewPagerThird.adapter = adapterThird
        viewPagerBottom.adapter = adapterBottom

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


    private inner class PhotoPagerAdapter(
        private val context: Context,
        private val images: Array<Int>,
        private val texts: Array<String>
    ) : PagerAdapter() {

        override fun getCount(): Int {
            return images.size
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.item_photo, container, false)
            val imageView = view.findViewById<ImageView>(R.id.imageView)
            val overlayView = view.findViewById<View>(R.id.overlayView)
            val textLayout = view.findViewById<LinearLayout>(R.id.textLayout)
            val textView = view.findViewById<TextView>(R.id.textView)

            imageView.setImageResource(images[position])

            if (position == 0) {
                overlayView.visibility = View.VISIBLE
                textLayout.visibility = View.VISIBLE
                textView.text = texts[position]

                imageView.setOnClickListener {
                    toggleOverlayViewVisibility(overlayView, textLayout)
                }
            } else {
                imageView.setOnClickListener(null)
            }

            container.addView(view)
            return view
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }
    }

    private fun toggleOverlayViewVisibility(overlayView: View, textLayout: LinearLayout) {
        if (overlayView.visibility == View.GONE) {
            overlayView.visibility = View.VISIBLE
            textLayout.visibility = View.VISIBLE
        } else {
            overlayView.visibility = View.GONE
            textLayout.visibility = View.GONE
        }
    }
}
