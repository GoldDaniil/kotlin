package com.example.myapplication

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class GrandPrixCalculatorActivity : AppCompatActivity() {

    private val grandPrixDates = mapOf(
        "Бахрейн" to "2024-03-02 18:00:00",
        "Саудовская Аравия" to "2024-03-09 20:00:00",
        "Австралия" to "2024-03-24 07:00:00",
        "Япония" to "2024-04-07 08:00:00",
        "Китай" to "2024-04-21 10:00:00",
        "Майами" to "2024-05-05 23:00:00",
        "Эмилии-Романьи" to "2024-05-19 16:00:00",
        "Монако" to "2024-05-26 16:00:00",
        "Канада" to "2024-06-09 21:00:00",
        "Испания" to "2024-06-23 16:00:00",
        "Австрия" to "2024-06-30 16:00:00",
        "Великобритания" to "2024-07-07 17:00:00",
        "Венгрия" to "2024-07-21 16:00:00",
        "Бельгия" to "2024-07-28 16:00:00",
        "Нидерланды" to "2024-08-25 16:00:00",
        "Италия" to "2024-09-01 16:00:00",
        "Азербайджан" to "2024-09-15 14:00:00",
        "Сингапур" to "2024-09-22 15:00:00",
        "США" to "2024-10-20 22:00:00",
        "Мексика" to "2024-10-27 23:00:00",
        "Бразилия" to "2024-11-03 20:00:00",
        "Лас-Вегас" to "2024-11-24 09:00:00",
        "Катар" to "2024-12-01 20:00:00",
        "Абу-Даби" to "2024-12-08 16:00:00",
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grand_prix_calculator)

        val grandPrixSpinner = findViewById<Spinner>(R.id.grandPrixSpinner)
        val calculateButton = findViewById<Button>(R.id.calculateButton)
        val resultTextView = findViewById<TextView>(R.id.resultTextView)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.grand_prix_array,
            R.layout.spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        grandPrixSpinner.adapter = adapter

        calculateButton.setOnClickListener {
            val selectedGrandPrix = grandPrixSpinner.selectedItem.toString()
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
                return "Гран при уже прошел ${dateFormat.format(eventDate)}"
            }

            val seconds = diff / 1000 % 60
            val minutes = diff / (1000 * 60) % 60
            val hours = diff / (1000 * 60 * 60) % 24
            val days = diff / (1000 * 60 * 60 * 24)

            return "Времени осталось до гран при: $days дней, $hours часов, $minutes минут, $seconds секунд"
        }

        return "ошибка расчета времени"
    }
}
