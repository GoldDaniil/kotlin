package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class QuizGameActivity : AppCompatActivity() {

    private val delayMillis: Long = 80 // 60/1000 секунды

    private val questions = listOf(
        "Кто изображен на фото?",
        "Кто изображен на фото?",
        "Что изображено на фото?"
    )

    private val options = listOf(
        listOf("Джузеппе Фарина", "Феррари", "Михаэль Шумахер", "Льюис Хэмилтон"),
        listOf("Себастьян Феттель", "Фернандо Алонсо", "Макс Ферстаппен", "Кими Райкконен"),
        listOf("Себастьян Феттель", "Шины", "крыло", "суппорт")
    )

    private val answers = listOf(
        "Джузеппе Фарина",
        "Себастьян Феттель",
        "Шины"
    )

    private val imageResources = listOf(
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3
    )

    private var currentQuestionIndex = 0
    private var correctAnswers = 0
    private var wrongAnswers = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_game)

        showQuestion()

        val buttonA: Button = findViewById(R.id.buttonA)
        val buttonB: Button = findViewById(R.id.buttonB)
        val buttonC: Button = findViewById(R.id.buttonC)
        val buttonD: Button = findViewById(R.id.buttonD)
        val retryButton: Button = findViewById(R.id.retryButton)

        buttonA.setOnClickListener {
            checkAnswer(buttonA.text.toString(), buttonA, listOf(buttonB, buttonC, buttonD))
        }

        buttonB.setOnClickListener {
            checkAnswer(buttonB.text.toString(), buttonB, listOf(buttonA, buttonC, buttonD))
        }

        buttonC.setOnClickListener {
            checkAnswer(buttonC.text.toString(), buttonC, listOf(buttonA, buttonB, buttonD))
        }

        buttonD.setOnClickListener {
            checkAnswer(buttonD.text.toString(), buttonD, listOf(buttonA, buttonB, buttonC))
        }

        retryButton.setOnClickListener {
            resetGame()
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

    private fun showQuestion() {
        val questionTextView: TextView = findViewById(R.id.questionTextView)
        questionTextView.text = questions[currentQuestionIndex]

        val buttonA: Button = findViewById(R.id.buttonA)
        val buttonB: Button = findViewById(R.id.buttonB)
        val buttonC: Button = findViewById(R.id.buttonC)
        val buttonD: Button = findViewById(R.id.buttonD)
        val questionImageView: ImageView = findViewById(R.id.questionImageView)

        buttonA.text = options[currentQuestionIndex][0]
        buttonB.text = options[currentQuestionIndex][1]
        buttonC.text = options[currentQuestionIndex][2]
        buttonD.text = options[currentQuestionIndex][3]

        questionImageView.setImageResource(imageResources[currentQuestionIndex])
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

    private fun checkAnswer(answer: String, selectedButton: Button, otherButtons: List<Button>) {
        val correctAnswer = answers[currentQuestionIndex]
        if (correctAnswer.equals(answer, ignoreCase = true)) {
            correctAnswers++
            selectedButton.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_green_light))
        } else {
            wrongAnswers++
            selectedButton.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_red_light))
            otherButtons.forEach { button ->
                if (button.text.toString() == correctAnswer) {
                    button.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_green_light))
                }
            }
        }
        updateScore()

        selectedButton.postDelayed({
            resetButtonColors()
            currentQuestionIndex++
            if (currentQuestionIndex < questions.size) {
                showQuestion()
            } else {
                endGame()
            }
        }, 1000)
    }

    private fun resetButtonColors() {
        val buttonA: Button = findViewById(R.id.buttonA)
        val buttonB: Button = findViewById(R.id.buttonB)
        val buttonC: Button = findViewById(R.id.buttonC)
        val buttonD: Button = findViewById(R.id.buttonD)

        buttonA.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
        buttonB.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
        buttonC.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
        buttonD.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
    }

    private fun updateScore() {
        val correctAnswersTextView: TextView = findViewById(R.id.correctAnswersTextView)
        val wrongAnswersTextView: TextView = findViewById(R.id.wrongAnswersTextView)
        correctAnswersTextView.text = "Верные ответы: $correctAnswers"
        wrongAnswersTextView.text = "Неправильные ответы: $wrongAnswers"
    }

    private fun endGame() {
        val questionCardView: View = findViewById(R.id.questionCardView)
        val topButtonsLayout: View = findViewById(R.id.top_buttons)
        val bottomButtonsLayout: View = findViewById(R.id.bottom_buttons)
        val scoreLayout: View = findViewById(R.id.score_layout)
        val resultTextView: TextView = findViewById(R.id.resultTextView)
        val retryButton: Button = findViewById(R.id.retryButton)

        questionCardView.visibility = View.GONE
        topButtonsLayout.visibility = View.GONE
        bottomButtonsLayout.visibility = View.GONE
        scoreLayout.visibility = View.GONE
        resultTextView.visibility = View.VISIBLE
        retryButton.visibility = View.VISIBLE

        resultTextView.text = "Ваш результат: $correctAnswers верных ответов"
    }

    private fun resetGame() {
        currentQuestionIndex = 0
        correctAnswers = 0
        wrongAnswers = 0

        val questionCardView: View = findViewById(R.id.questionCardView)
        val topButtonsLayout: View = findViewById(R.id.top_buttons)
        val bottomButtonsLayout: View = findViewById(R.id.bottom_buttons)
        val scoreLayout: View = findViewById(R.id.score_layout)
        val resultTextView: TextView = findViewById(R.id.resultTextView)
        val retryButton: Button = findViewById(R.id.retryButton)

        questionCardView.visibility = View.VISIBLE
        topButtonsLayout.visibility = View.VISIBLE
        bottomButtonsLayout.visibility = View.VISIBLE
        scoreLayout.visibility = View.VISIBLE
        resultTextView.visibility = View.GONE
        retryButton.visibility = View.GONE

        updateScore()
        showQuestion()
    }
}
