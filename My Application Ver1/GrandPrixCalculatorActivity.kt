package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class GrandPrixCalculatorActivity : AppCompatActivity() {

    private val grandPrixDates = mapOf(
        "Австралия" to "2024-03-17 14:00:00",
        "Монако" to "2024-05-26 15:00:00",
        "Майами" to "2024-05-05 15:30:00"
        // Добавьте остальные даты Гран-при здесь
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grand_prix_calculator)

        val grandPrixSpinner = findViewById<Spinner>(R.id.grandPrixSpinner)
        val calculateButton = findViewById<Button>(R.id.calculateButton)
        val resultTextView = findViewById<TextView>(R.id.resultTextView)

        calculateButton.setOnClickListener {
            val selectedGrandPrix = grandPrixSpinner.selectedItem.toString()
            val grandPrixDate = grandPrixDates[selectedGrandPrix]

            if (grandPrixDate != null) {
                val timeRemaining = calculateTimeRemaining(grandPrixDate)
                resultTextView.text = "Time remaining: $timeRemaining"
            } else {
                resultTextView.text = "Invalid Grand Prix selected"
            }
        }
    }

    private fun calculateTimeRemaining(grandPrixDate: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val eventDate = dateFormat.parse(grandPrixDate)
        val currentDate = Date()

        if (eventDate != null) {
            val diff = eventDate.time - currentDate.time
            val seconds = diff / 1000 % 60
            val minutes = diff / (1000 * 60) % 60
            val hours = diff / (1000 * 60 * 60) % 24
            val days = diff / (1000 * 60 * 60 * 24) % 7
            val weeks = diff / (1000 * 60 * 60 * 24 * 7)
            val months = weeks / 4 // Примерное число месяцев

            return "$months months, $weeks weeks, $days days, $hours hours, $minutes minutes, $seconds seconds"
        }

        return "Error calculating time remaining"
    }
}
