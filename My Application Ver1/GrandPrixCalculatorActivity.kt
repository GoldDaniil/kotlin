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
        "бахрейн" to "2024-03-02 18:00:00",
        "саудовская аравия" to "2024-03-09 20:00:00",
        "австралия" to "2024-03-24 07:00:00",
        "япония" to "2024-04-07 08:00:00",
        "китай" to "2024-04-21 10:00:00",
        "майами" to "2024-05-05 23:00:00",
        "эмилии-романьи" to "2024-05-19 16:00:00",
        "монако" to "2024-05-26 16:00:00",
        "канада" to "2024-06-09 21:00:00",
        "испания" to "2024-06-23 16:00:00",
        "австрия" to "2024-06-30 16:00:00",
        "великобритания" to "2024-07-07 17:00:00",
        "венгрия" to "2024-07-21 16:00:00",
        "бельгия" to "2024-07-28 16:00:00",
        "нидерланды" to "2024-08-25 16:00:00",
        "италия" to "2024-09-01 16:00:00",
        "азербайджан" to "2024-09-15 14:00:00",
        "сингапур" to "2024-09-22 15:00:00",
        "сша" to "2024-10-20 22:00:00",
        "мексика" to "2024-10-27 23:00:00",
        "бразилия" to "2024-11-03 20:00:00",
        "лас-вегас" to "2024-11-24 09:00:00",
        "катар" to "2024-12-01 20:00:00",
        "абу-даби" to "2024-12-08 16:00:00",
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grand_prix_calculator)

        val grandPrixSpinner = findViewById<Spinner>(R.id.grandPrixSpinner)
        val calculateButton = findViewById<Button>(R.id.calculateButton)
        val resultTextView = findViewById<TextView>(R.id.resultTextView)

        calculateButton.setOnClickListener {
            val selectedGrandPrix = grandPrixSpinner.selectedItem.toString().trim().lowercase()
            val grandPrixDate = grandPrixDates[selectedGrandPrix]

            if (grandPrixDate != null) {
                val timeRemaining = calculateTimeRemaining(grandPrixDate)
                resultTextView.text = timeRemaining
            } else {
                resultTextView.text = "Выбран неверный Гран-при"
            }
        }
    }

    private fun calculateTimeRemaining(grandPrixDate: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val eventDate = dateFormat.parse(grandPrixDate)
        val currentDate = Date()

        if (eventDate != null) {
            val diff = eventDate.time - currentDate.time

            if (diff <= 0) {
                val formattedEventDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(eventDate)
                return "Гран при уже прошел $formattedEventDate"
            }

            val seconds = diff / 1000 % 60
            val minutes = diff / (1000 * 60) % 60
            val hours = diff / (1000 * 60 * 60) % 24
            val days = diff / (1000 * 60 * 60 * 24) % 7
            val weeks = diff / (1000 * 60 * 60 * 24 * 7)
            val months = weeks / 4

            return "Времени осталось до гран при: $months месяцев, $weeks недель, $days дней, $hours часов, $minutes минут, $seconds секунд"
        }

        return "Ошибка расчета времени"
    }
}
