package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val newsList = listOf(
            "Поздравления Шарлю Леклеру и Ferrari!",
            "Событие 2",
            "Новость 3",
            "Событие 4",
            "Новость 5"
        )

        val newsDetails = listOf(
            "   В Pirelli вместе со всеми поздравляют Шарля Леклера с победой в домашнем для гонщика Ferrari Гран При Монако, разумеется, подчеркнув, что после рестарта шины составов Hard и Medium позволили большинству гонщиков доехать до финиша, выдержав 77 кругов.\n" +
                    "\n" +
                    "Марио Изола, директор Pirelli Motorsport: «Прежде всего поздравляем Шарля Леклера и Ferrari с их победой! Мы только сейчас начинаем понимать, как много этот успех значит для Шарля, ведь он выиграл гонку на улицах родного города.\n" +
                    "\n" +
                    "Если говорить о работе шин, то в Монако обычно не так уж много вариантов тактики, а сегодня и эти возможности были фактически перечёркнуты из-за того, что уже на первом круге гонка была остановлена красными флагами. Фактически это означало, что пит-стопов больше проводить не нужно.\n" +
                    "\n" +
                    "Мы знали, что резина обоих составов, как Medium, так и Hard, позволит проехать всю дистанцию, если правильно с ней работать, и большинству гонщиков это удалось.\n" +
                    "\n" +
                    "Пелотон разделился на группы в соответствии с тем, кто на каких шинах ехал, и внутри этих групп происходили своего рода игры в кошки-мышки, гонщики пытались заставить соперников ошибаться, однако не было ни обгонов, ни возможностей как-то изменить тактику, что в теории могло бы обострить ситуацию.",
            "Подробности события 2...",
            "Подробности новости 3...",
            "Подробности события 4...",
            "Подробности новости 5..."
        )

        val adapter = NewsAdapter(newsList, newsDetails)
        recyclerView.adapter = adapter
    }
}
