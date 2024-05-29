package com.example.myapplication

import android.widget.LinearLayout
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

class PhotoGalleryActivity : AppCompatActivity() {

    private val imagesTop = arrayOf(
        R.drawable.photo1, R.drawable.photo2, R.drawable.photo3, R.drawable.photo4,
        R.drawable.photo5, R.drawable.photo6, R.drawable.photo7, R.drawable.photo8
    )
    private val imagesSecond = arrayOf(
        R.drawable.tech10, R.drawable.tech1, R.drawable.tech2, R.drawable.tech3, R.drawable.tech4,
        R.drawable.tech5, R.drawable.tech6, R.drawable.tech7, R.drawable.tech8, R.drawable.tech9
    )
    private val imagesThird = arrayOf(
        R.drawable.photo9, R.drawable.photo10, R.drawable.photo11, R.drawable.photo12
    )
    private val imagesBottom = arrayOf(
        R.drawable.crash1, R.drawable.crash2, R.drawable.crash3, R.drawable.crash4, R.drawable.crash5,
        R.drawable.crash6, R.drawable.crash7, R.drawable.crash8
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_gallery)

        val viewPagerTop = findViewById<ViewPager>(R.id.viewPagerTop)
        val viewPagerSecond = findViewById<ViewPager>(R.id.viewPagerSecond)
        val viewPagerThird = findViewById<ViewPager>(R.id.viewPagerThird)
        val viewPagerBottom = findViewById<ViewPager>(R.id.viewPagerBottom)

        val adapterTop = PhotoPagerAdapter(this, imagesTop)
        val adapterSecond = PhotoPagerAdapter(this, imagesSecond)
        val adapterThird = PhotoPagerAdapter(this, imagesThird)
        val adapterBottom = PhotoPagerAdapter(this, imagesBottom)

        viewPagerTop.adapter = adapterTop
        viewPagerSecond.adapter = adapterSecond
        viewPagerThird.adapter = adapterThird
        viewPagerBottom.adapter = adapterBottom
    }

    private inner class PhotoPagerAdapter(
        private val context: Context,
        private val images: Array<Int>
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

            imageView.setImageResource(images[position])

            if (position == 0) { 
                imageView.setOnClickListener {
                    toggleOverlayViewVisibility(overlayView, textLayout)
                }
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
