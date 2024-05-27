package com.example.myapplication

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_gallery)

        val viewPagerTop = findViewById<ViewPager>(R.id.viewPagerTop)
        val viewPagerSecond = findViewById<ViewPager>(R.id.viewPagerSecond)
        val viewPagerThird = findViewById<ViewPager>(R.id.viewPagerThird)
        val viewPagerBottom = findViewById<ViewPager>(R.id.viewPagerBottom)

        val imagesTop = arrayOf(
            R.drawable.photo1, R.drawable.photo2, R.drawable.photo3, R.drawable.photo4
        )
        val imagesSecond = arrayOf(
            R.drawable.photo5, R.drawable.photo6, R.drawable.photo7, R.drawable.photo8
        )
        val imagesThird = arrayOf(
            R.drawable.photo9, R.drawable.photo10, R.drawable.photo11, R.drawable.photo12
        )
        val imagesBottom = arrayOf(
            R.drawable.photo13, R.drawable.photo14, R.drawable.photo15, R.drawable.photo16, R.drawable.photo17
        )

        val adapterTop = PhotoPagerAdapter(this, imagesTop)
        val adapterSecond = PhotoPagerAdapter(this, imagesSecond)
        val adapterThird = PhotoPagerAdapter(this, imagesThird)
        val adapterBottom = PhotoPagerAdapter(this, imagesBottom)

        viewPagerTop.adapter = adapterTop
        viewPagerSecond.adapter = adapterSecond
        viewPagerThird.adapter = adapterThird
        viewPagerBottom.adapter = adapterBottom
    }

    private inner class PhotoPagerAdapter(private val context: Context, private val images: Array<Int>) : PagerAdapter() {

        override fun getCount(): Int {
            return images.size
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.item_photo, container, false)
            val imageView = view.findViewById<ImageView>(R.id.imageView)
            imageView.setImageResource(images[position])
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
}
