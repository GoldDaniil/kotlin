package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class YouTubeVideosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube_videos)

        val countrySpinner = findViewById<Spinner>(R.id.countrySpinner)
        val watchButton = findViewById<Button>(R.id.watchButton)

        val countries = arrayOf("Бахрейн", "Австралия", "Монако", "Саудовская Аравия", "Азербайджан", "Испания", "Италия", "Япония", "Эмилии-Романьи", "Австрия", "Великобритания")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, countries)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        countrySpinner.adapter = adapter

        countrySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedCountry = parent.getItemAtPosition(position).toString()
                val videoId = when (selectedCountry) {
                    "Бахрейн" -> R.raw.video_grand_prix_bahrain
                    "Австралия" -> R.raw.video_grand_prix_australia
                    "Монако" -> R.raw.video_grand_prix_monako
                    "Саудовская Аравия" -> R.raw.video_grand_prix_saudia_aravia
                    "Азербайджан" -> R.raw.video_grand_prix_azer
                    "Испания" -> R.raw.video_grand_prix_italy
                    "Италия" -> R.raw.video_grand_prix_italy
                    "Япония" -> R.raw.video_grand_prix_italy
                    "Эмилии-Романьи" -> R.raw.video_grand_prix_emiliiromanii
                    "Австрия" -> R.raw.video_grand_prix_italy
                    "Великобритания" -> R.raw.video_grand_prix_emiliiromanii
                    else -> R.raw.gradvideopast

                }
                watchButton.setOnClickListener {
                    val intent = Intent(this@YouTubeVideosActivity, FullScreenVideoActivity::class.java)
                    intent.putExtra("videoId", videoId)
                    startActivity(intent)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // обработка - если ничего не выбрано
            }
        }
    }
}
