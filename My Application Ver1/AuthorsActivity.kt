package com.example.myapplication

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AuthorsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authors)

        val authorsTextView = findViewById<TextView>(R.id.authorsTextView)

        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        authorsTextView.startAnimation(animation)
    }
}
