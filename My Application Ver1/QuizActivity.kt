package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity() {

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
            checkAnswer("true")
        }

        buttonFalse.setOnClickListener {
            checkAnswer("false")
        }
    }

    private fun showQuestion() {
        val questionTextView: TextView = findViewById(R.id.questionTextView)
        questionTextView.text = questions[currentQuestionIndex]
    }

    private fun checkAnswer(answer: String) {
        val correctAnswer = answers[currentQuestionIndex]
        if (correctAnswer.equals(answer, ignoreCase = true)) {
            Toast.makeText(this, "Правильно!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Неправильно!", Toast.LENGTH_SHORT).show()
        }

        currentQuestionIndex = (currentQuestionIndex + 1) % questions.size
        showQuestion()
    }
}
