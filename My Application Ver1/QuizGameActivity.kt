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

    private val delayMillis: Long = 1000 // 1 секунда
    data class QuizQuestion(val question: String, val options: List<String>, val correctAnswer: String)

    private val questions = listOf(
        QuizQuestion("Кто изображен на фото?", listOf("Джузеппе Фарина", "Феррари", "Михаэль Шумахер", "Льюис Хэмилтон"), "Джузеппе Фарина"),
        QuizQuestion("Какая команда выиграла чемпионат мира 2019 года?", listOf("Феррари", "Мерседес", "Ред Булл", "Макларен"), "Мерседес"),
    )

    private val answers = listOf(
        "Джузеппе Фарина",
        "Феррари",
        "Макс Ферстаппен",
    )

    private var currentQuestionIndex = 0
    private var correctAnswers = 0
    private var wrongAnswers = 0

    private lateinit var questionImageView: ImageView
    private lateinit var buttonA: Button
    private lateinit var buttonB: Button
    private lateinit var buttonC: Button
    private lateinit var buttonD: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_game)

        questionImageView = findViewById(R.id.questionImageView)
        buttonA = findViewById(R.id.buttonA)
        buttonB = findViewById(R.id.buttonB)
        buttonC = findViewById(R.id.buttonC)
        buttonD = findViewById(R.id.buttonD)

        showQuestion()

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

    }

    private fun showQuestion() {
        val questionTextView: TextView = findViewById(R.id.questionTextView)
        questionTextView.text = questions[currentQuestionIndex].question

        val buttonA: Button = findViewById(R.id.buttonA)
        val buttonB: Button = findViewById(R.id.buttonB)
        val buttonC: Button = findViewById(R.id.buttonC)
        val buttonD: Button = findViewById(R.id.buttonD)

        val currentQuestion = questions[currentQuestionIndex]
        buttonA.text = currentQuestion.options[0]
        buttonB.text = currentQuestion.options[1]
        buttonC.text = currentQuestion.options[2]
        buttonD.text = currentQuestion.options[3]
    }

    private fun checkAnswer(answer: String, selectedButton: Button, otherButtons: List<Button>) {
        val correctAnswer = questions[currentQuestionIndex].correctAnswer
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
