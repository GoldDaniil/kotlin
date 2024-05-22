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
        val overlayView1 = findViewById<View>(R.id.overlayView1)
        val textView1 = findViewById<TextView>(R.id.textView1)

        val imageView2 = findViewById<ImageView>(R.id.imageView2)
        val overlayView2 = findViewById<View>(R.id.overlayView2)
        val textView2 = findViewById<TextView>(R.id.textView2)

        val imageView3 = findViewById<ImageView>(R.id.imageView3)
        val overlayView3 = findViewById<View>(R.id.overlayView3)
        val textView3 = findViewById<TextView>(R.id.textView3)

        val imageView4 = findViewById<ImageView>(R.id.imageView4)
        val overlayView4 = findViewById<View>(R.id.overlayView4)
        val textView4 = findViewById<TextView>(R.id.textView4)

        val imageView5 = findViewById<ImageView>(R.id.imageView5)
        val overlayView5 = findViewById<View>(R.id.overlayView5)
        val textView5 = findViewById<TextView>(R.id.textView5)

        val textViews = listOf(textView1, textView2, textView3, textView4, textView5)
        val overlayViews = listOf(overlayView1, overlayView2, overlayView3, overlayView4, overlayView5)

        imageView1.setOnClickListener {
            toggleTextViewVisibility(textView1, overlayView1, textViews, overlayViews)
        }

        imageView2.setOnClickListener {
            toggleTextViewVisibility(textView2, overlayView2, textViews, overlayViews)
        }

        imageView3.setOnClickListener {
            toggleTextViewVisibility(textView3, overlayView3, textViews, overlayViews)
        }

        imageView4.setOnClickListener {
            toggleTextViewVisibility(textView4, overlayView4, textViews, overlayViews)
        }

        imageView5.setOnClickListener {
            toggleTextViewVisibility(textView5, overlayView5, textViews, overlayViews)
        }
    }

    private fun toggleTextViewVisibility(textView: TextView, overlayView: View, allTextViews: List<TextView>, allOverlayViews: List<View>) {
        for (i in allTextViews.indices) {
            if (allTextViews[i].visibility == View.VISIBLE && allTextViews[i] != textView) {
                allTextViews[i].animate().alpha(0f).setDuration(300).withEndAction {
                    allTextViews[i].visibility = View.GONE
                }
                allOverlayViews[i].animate().alpha(0f).setDuration(300).withEndAction {
                    allOverlayViews[i].visibility = View.GONE
                }
            }
        }

        if (textView.visibility == View.GONE) {
            textView.alpha = 0f
            textView.visibility = View.VISIBLE
            textView.animate().alpha(1f).setDuration(300)

            overlayView.alpha = 0f
            overlayView.visibility = View.VISIBLE
            overlayView.animate().alpha(1f).setDuration(300)
        } else {
            textView.animate().alpha(0f).setDuration(300).withEndAction {
                textView.visibility = View.GONE
            }
            overlayView.animate().alpha(0f).setDuration(300).withEndAction {
                overlayView.visibility = View.GONE
            }
        }
    }
}
