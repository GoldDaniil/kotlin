package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SearchResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        val imageView1 = findViewById<ImageView>(R.id.imageView1)
        val textView1 = findViewById<TextView>(R.id.textView1)

        val imageView2 = findViewById<ImageView>(R.id.imageView2)
        val textView2 = findViewById<TextView>(R.id.textView2)

        val imageView3 = findViewById<ImageView>(R.id.imageView3)
        val textView3 = findViewById<TextView>(R.id.textView3)

        val imageView4 = findViewById<ImageView>(R.id.imageView4)
        val textView4 = findViewById<TextView>(R.id.textView4)

        val imageView5 = findViewById<ImageView>(R.id.imageView5)
        val textView5 = findViewById<TextView>(R.id.textView5)

        val textViews = listOf(textView1, textView2, textView3, textView4, textView5)

        imageView1.setOnClickListener {
            toggleTextViewVisibility(textView1, textViews)
        }

        imageView2.setOnClickListener {
            toggleTextViewVisibility(textView2, textViews)
        }

        imageView3.setOnClickListener {
            toggleTextViewVisibility(textView3, textViews)
        }

        imageView4.setOnClickListener {
            toggleTextViewVisibility(textView4, textViews)
        }

        imageView5.setOnClickListener {
            toggleTextViewVisibility(textView5, textViews)
        }
    }

    private fun toggleTextViewVisibility(textView: TextView, allTextViews: List<TextView>) {
        for (tv in allTextViews) {
            if (tv.visibility == View.VISIBLE && tv != textView) {
                tv.animate().alpha(0f).setDuration(300).withEndAction {
                    tv.visibility = View.GONE
                }
            }
        }

        if (textView.visibility == View.GONE) {
            textView.alpha = 0f
            textView.visibility = View.VISIBLE
            textView.animate().alpha(1f).setDuration(300)
        } else {
            textView.animate().alpha(0f).setDuration(300).withEndAction {
                textView.visibility = View.GONE
            }
        }
    }
}
