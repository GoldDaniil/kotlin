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

        imageView1.setOnClickListener {
            toggleTextViewVisibility(textView1)
        }

        imageView2.setOnClickListener {
            toggleTextViewVisibility(textView2)
        }

        imageView3.setOnClickListener {
            toggleTextViewVisibility(textView3)
        }

        imageView4.setOnClickListener {
            toggleTextViewVisibility(textView4)
        }

        imageView5.setOnClickListener {
            toggleTextViewVisibility(textView5)
        }
    }

    private fun toggleTextViewVisibility(textView: TextView) {
        if (textView.visibility == View.VISIBLE) {
            textView.visibility = View.GONE
        } else {
            textView.visibility = View.VISIBLE
        }
    }
}
