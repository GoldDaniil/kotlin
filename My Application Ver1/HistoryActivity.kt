package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.view.ViewTreeObserver
import android.animation.ObjectAnimator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.graphics.Typeface
import android.view.View
import android.content.Context
import android.widget.ScrollView

data class Article(val title: String, val description: String, val imageResId: Int)

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val tvTitle: TextView = findViewById(R.id.tvTitle)
        val articlesContainer: LinearLayout = findViewById(R.id.articlesContainer)

        tvTitle.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                tvTitle.viewTreeObserver.removeOnPreDrawListener(this)
                ObjectAnimator.ofFloat(tvTitle, "alpha", 0f, 1f).apply {
                    duration = 2000
                    interpolator = DecelerateInterpolator()
                    start()
                }
                return true
            }
        })

        val articles = listOf(
            Article("Рождение", "Как появился самый дорогой спорт в мире?", R.drawable.image1),
            Article("Команда Mercedes-AMG Petronas Formula One", "Доминируя в современной эпохе с Льюисом Хэмилтоном и теперь стремясь вперед с Джорджем Расселом.", R.drawable.mercedes),
            Article("Скудерия Феррари", "Одна из старейших и самых успешных команд в истории Формулы-1, всегда любима фанатами.", R.drawable.ferrari),
            Article("Ред Булл Рейсинг", "Известны своими агрессивными стратегиями и талантливыми гонщиками, такими как Макс Ферстаппен.", R.drawable.red_bull),
            Article("Команда McLaren F1", "Команда с богатой историей и многочисленными чемпионствами, стремящаяся вернуться к своей прежней славе.", R.drawable.mclaren),
            Article("Команда Alpine F1", "Ранее Renault, команда стремится к вершине под руководством Фернандо Алонсо.", R.drawable.alpine),
            Article("Скудерия AlphaTauri", "Сестринская команда Red Bull, развивающая молодых талантов, таких как Юки Цунода.", R.drawable.alpha_tauri),
            Article("Команда Aston Martin Cognizant Formula One", "Переименована из Racing Point, стремится к вершине с Себастьяном Феттелем.", R.drawable.aston_martin),
            Article("Команда Williams Racing", "Одна из самых знаковых команд, сейчас перестраивается и ищет путь к возвращению.", R.drawable.williams),
            Article("Команда Alfa Romeo Racing Orlen", "Последняя итерация Sauber, с сильным акцентом на будущее.", R.drawable.alfa_romeo),
            Article("Команда Haas F1", "Американская команда с сильным партнерством с Ferrari, стремящаяся к улучшению.", R.drawable.haas)
        )

        for (article in articles) {
            val articleView = createArticleView(this, article)
            articlesContainer.addView(articleView)
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

    private fun createArticleView(context: Context, article: Article): View {
        return LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16, 16, 16, 16)
            val marginLayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 24.dpToPx(context))
            }

            background = ContextCompat.getDrawable(context, R.drawable.article_background)
            elevation = 8f

            val imageView = ImageView(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    200.dpToPx(context)
                ).apply {
                    setMargins(0, 0, 0, 16.dpToPx(context))
                }
                scaleType = ImageView.ScaleType.CENTER_CROP
                setImageResource(article.imageResId)
            }

            val titleView = TextView(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 0, 0, 8.dpToPx(context))
                }
                textSize = 20f
                setPadding(0, 8, 0, 0)
                setTypeface(null, Typeface.BOLD)
                text = article.title
                setTextColor(ContextCompat.getColor(context, android.R.color.black))
            }

            val divider = View(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1.dpToPx(context)
                ).apply {
                    setMargins(0, 8.dpToPx(context), 0, 8.dpToPx(context))
                }
                setBackgroundColor(ContextCompat.getColor(context, android.R.color.darker_gray))
            }

            val descriptionView = TextView(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                textSize = 16f
                setPadding(0, 4, 0, 0)
                text = article.description
                setTextColor(ContextCompat.getColor(context, android.R.color.black))
            }

            addView(imageView)
            addView(titleView)
            addView(divider)
            addView(descriptionView)
            layoutParams = marginLayoutParams
        }
    }

    private fun Int.dpToPx(context: Context): Int {
        return (this * context.resources.displayMetrics.density).toInt()
    }



    private fun changeColorAndNavigateWithDelay(layout: LinearLayout, activityClass: Class<*>) {
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
        }, 200)
    }
}
