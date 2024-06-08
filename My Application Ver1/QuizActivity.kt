package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class QuizActivity : AppCompatActivity() {

    private val delayMillis: Long = 80 // 60/1000 секунды

    private val questions = listOf(
        "Кто выиграл первый чемпионат мира по Формуле-1?",
        "Какая команда выиграла больше всего чемпионатов?",
        "Кто является самым молодым победителем гонки Формулы-1?"
    )

    private val answers = listOf(
        "Джузеппе Фарина",
        "Феррари",
        "Макс Ферстаппен"
    )

    private var currentQuestionIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        showQuestion()

        val buttonTrue: Button = findViewById(R.id.buttonTrue)
        val buttonFalse: Button = findViewById(R.id.buttonFalse)

        buttonTrue.setOnClickListener {
            checkAnswer("true", buttonTrue, buttonFalse)
        }

        buttonFalse.setOnClickListener {
            checkAnswer("false", buttonTrue, buttonFalse)
        }

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

    private fun showQuestion() {
        val questionTextView: TextView = findViewById(R.id.questionTextView)
        questionTextView.text = questions[currentQuestionIndex]
    }

    private fun checkAnswer(answer: String, buttonTrue: Button, buttonFalse: Button) {
        val correctAnswer = answers[currentQuestionIndex]
        val correctButton = if (correctAnswer.equals(answer, ignoreCase = true)) buttonTrue else buttonFalse
        val incorrectButton = if (correctButton == buttonTrue) buttonFalse else buttonTrue

        correctButton.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_green_light))
        incorrectButton.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_red_light))

        Handler(Looper.getMainLooper()).postDelayed({
            currentQuestionIndex = (currentQuestionIndex + 1) % questions.size
            showQuestion()
            resetButtonColors(buttonTrue, buttonFalse)
        }, 1000) 
    }

    private fun resetButtonColors(buttonTrue: Button, buttonFalse: Button) {
        buttonTrue.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
        buttonFalse.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
    }
}
