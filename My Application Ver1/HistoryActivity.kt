package com.example.myapplication

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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
        val scrollView: ScrollView = findViewById(R.id.scrollView)
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
            Article("Article 1", "Description 1", R.drawable.image1),
            Article("Article 2", "Description 2", R.drawable.image2)
        )

        for (article in articles) {
            val articleView = createArticleView(this, article)
            articlesContainer.addView(articleView)
        }
    }

    private fun createArticleView(context: Context, article: Article): View {
        return LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16, 16, 16, 16)

            val imageView = ImageView(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    200.dpToPx(context)
                )
                scaleType = ImageView.ScaleType.CENTER_CROP
                setImageResource(article.imageResId)
            }

            val titleView = TextView(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                textSize = 18f
                setPadding(0, 8, 0, 0)
                setTypeface(null, Typeface.BOLD)
                text = article.title
            }

            val descriptionView = TextView(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                textSize = 14f
                setPadding(0, 4, 0, 0)
                text = article.description
            }

            addView(imageView)
            addView(titleView)
            addView(descriptionView)
        }
    }

    // Преобразование dp в px
    private fun Int.dpToPx(context: Context): Int {
        return (this * context.resources.displayMetrics.density).toInt()
    }
}
