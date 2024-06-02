package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AuthorsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authors)

        val authorsTextView = findViewById<TextView>(R.id.authorsTextView)
        val imageContainer = findViewById<View>(R.id.imageContainer)

        val textAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        authorsTextView.startAnimation(textAnimation)

        val containerAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in_delayed)
        containerAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                findViewById<ImageView>(R.id.imageView1).apply {
                    visibility = View.VISIBLE
                    startAnimation(AnimationUtils.loadAnimation(this@AuthorsActivity, R.anim.fade_in))
                }
                findViewById<ImageView>(R.id.imageView2).apply {
                    visibility = View.VISIBLE
                    startAnimation(AnimationUtils.loadAnimation(this@AuthorsActivity, R.anim.fade_in))
                }
            }
        })
        imageContainer.startAnimation(containerAnimation)
    }
}
