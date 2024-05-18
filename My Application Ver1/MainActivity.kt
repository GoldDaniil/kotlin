package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var isDarkMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val mainLayout = findViewById<ConstraintLayout>(R.id.main)
        val textView = findViewById<TextView>(R.id.textView)
        val additionalTextView = findViewById<TextView>(R.id.additionalTextView)

        textView.setOnClickListener {
            val intent = Intent(this, SearchResultActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mainLayout.setOnClickListener {
            if (isDarkMode) {
                mainLayout.setBackgroundColor(Color.WHITE)
                textView.setTextColor(Color.BLACK)
                additionalTextView.visibility = View.GONE
            } else {
                mainLayout.setBackgroundColor(Color.BLACK)
                textView.setTextColor(Color.WHITE)
                additionalTextView.visibility = View.VISIBLE
            }
            isDarkMode = !isDarkMode
        }
    }
}
