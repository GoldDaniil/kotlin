package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.*
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlin.math.*
import android.media.MediaPlayer

class LoopGameActivity : AppCompatActivity() {

    private val delayMillis: Long = 80 // 60/1000 секунды

    private var mediaPlayer: MediaPlayer? = null

    private lateinit var surfaceView: SurfaceView
    private lateinit var surfaceHolder: SurfaceHolder
    private lateinit var drawingThread: DrawingThread

    private val circleRadius = 450 // Увеличиваем радиус круга
    private val circleThickness = 15 // Увеличиваем толщину обводки круга

    private val gray = Color.rgb(169, 169, 169)
    private val beige = Color.rgb(245, 245, 220)
    private val darkBeige = Color.rgb(222, 184, 135)
    private val green = Color.rgb(0, 255, 0)
    private val red = Color.rgb(255, 0, 0)
    private val black = Color.rgb(0, 0, 0)

    private val speedOptions = listOf(360 / 3.0, 360 / 2.5, 360 / 2.0, 360 / 1.5)
    private val arcLengthRange = IntRange(10, 60)

    private var angle = 0.0
    private var speed = speedOptions.random()
    private var arcLength = arcLengthRange.random()
    private var score = 0
    private var bestScore = 0 // Переменная для хранения лучшего результата
    private var gameOver = false
    private var startAngle = (0..(360 - arcLength)).random()

    private lateinit var icon: Bitmap
    private var dynamicScoreDisplay = ""
    private var dynamicScoreY = 0f
    private var dynamicScoreAlpha = 0f
    private val dynamicScorePaint = Paint().apply {
        color = Color.GREEN
        textSize = 70f
        textAlign = Paint.Align.CENTER
    }

    private val FPS = 60 // Константа для количества кадров в секунду

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loop_game)

        val rawResourceId = R.raw.marimba_odinochnyiy

        sharedPreferences = getSharedPreferences("LoopGamePrefs", Context.MODE_PRIVATE)
        bestScore = sharedPreferences.getInt("bestScore", 0) // Загрузка лучшего результата

        val iconDrawable = resources.getDrawable(R.drawable.helmet_icon, null)
        icon = Bitmap.createBitmap(iconDrawable.intrinsicWidth, iconDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(icon)
        iconDrawable.setBounds(0, 0, canvas.width, canvas.height)
        iconDrawable.draw(canvas)

        surfaceView = findViewById(R.id.surfaceView)
        surfaceHolder = surfaceView.holder

        drawingThread = DrawingThread()
        drawingThread.start()

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

    fun openSearchResultActivity(view: View) {
        startActivity(Intent(this, SearchResultActivity::class.java))
    }

    fun openNewsActivity(view: View) {
        startActivity(Intent(this, NewsActivity::class.java))
    }

    fun openYouTubeVideosActivity(view: View) {
        startActivity(Intent(this, YouTubeVideosActivity::class.java))
    }

    fun openGreenBackgroundActivity(view: View) {
        startActivity(Intent(this, QuizActivity::class.java))
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

    private val buttonPaint = Paint().apply {
        color = Color.LTGRAY
        style = Paint.Style.FILL
    }

    private val buttonTextPaint = Paint().apply {
        color = Color.BLACK
        textSize = 50f
        textAlign = Paint.Align.CENTER
    }

    private val buttonRect = RectF()

    private inner class DrawingThread : Thread() {
        private var running = false
        private var targetScore = score
        private val handler = Handler(Looper.getMainLooper())

        fun stopThread() {
            running = false
        }

        override fun run() {
            running = true
            while (running) {
                val canvas = surfaceHolder.lockCanvas()
                if (canvas != null) {
                    draw(canvas)
                    surfaceHolder.unlockCanvasAndPost(canvas)
                }
                try {
                    sleep((1000 / FPS).toLong())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }

        private fun draw(canvas: Canvas) {
            canvas.drawColor(Color.WHITE)

            if (!gameOver) {
                val centerX = canvas.width / 2
                val centerY = canvas.height / 2

                val outerRadius = circleRadius + circleThickness
                val innerRadius = circleRadius - circleThickness

                val grayPaint = Paint().apply {
                    style = Paint.Style.STROKE
                    color = gray
                    strokeWidth = circleThickness.toFloat()
                }
                canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), circleRadius.toFloat(), grayPaint)

                val beigePaint = Paint().apply {
                    style = Paint.Style.FILL
                    color = beige
                }
                canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), (circleRadius - circleThickness).toFloat(), beigePaint)

                val startAngleRad = Math.toRadians(startAngle.toDouble())
                val arcLengthRad = Math.toRadians(arcLength.toDouble())
                val endAngleRad = startAngleRad + arcLengthRad

                val borderPaint = Paint().apply {
                    style = Paint.Style.FILL
                    color = darkBeige
                }

                val path = Path()
                path.moveTo(centerX.toFloat(), centerY.toFloat())

                val startX = centerX + (outerRadius * cos(startAngleRad)).toFloat()
                val startY = centerY - (outerRadius * sin(startAngleRad)).toFloat()
                path.lineTo(startX, startY)

                val rectF = RectF(
                    (centerX - outerRadius).toFloat(),
                    (centerY - outerRadius).toFloat(),
                    (centerX + outerRadius).toFloat(),
                    (centerY + outerRadius).toFloat()
                )

                path.arcTo(rectF, -startAngle.toFloat(), -arcLength.toFloat())

                path.close()
                canvas.drawPath(path, borderPaint)

                val iconWidth = icon.width.toFloat()
                val iconHeight = icon.height.toFloat()
                val angleRad = Math.toRadians(angle)
                val xIcon = centerX + (innerRadius * cos(angleRad)).toInt() - (iconWidth / 2)
                val yIcon = centerY - (innerRadius * sin(angleRad)).toInt() - (iconHeight / 2)
                canvas.drawBitmap(icon, xIcon.toFloat(), yIcon.toFloat(), null)

                val previousAngle = angle
                angle = (angle + speed / FPS) % 360

                if (previousAngle > angle) {
                    resetZoneAndSpeed()
                }

                if (targetScore != score) {
                    targetScore = score
                    dynamicScoreDisplay = "+1"
                    handler.postDelayed({
                        dynamicScoreDisplay = ""
                    }, 500)
                }
            } else {
                val textPaint = Paint().apply {
                    color = red
                    textSize = 75f
                    textAlign = Paint.Align.CENTER
                }
                canvas.drawText("ХА! лох", (canvas.width / 2).toFloat(), (canvas.height / 2).toFloat(), textPaint)

                // Отображение кнопки "Сыграть еще раз?"
                val buttonWidth = 600
                val buttonHeight = 100
                val buttonX = (canvas.width / 2) - (buttonWidth / 2)
                val buttonY = (canvas.height / 2) + 100

                buttonRect.set(buttonX.toFloat(), buttonY.toFloat(), (buttonX + buttonWidth).toFloat(), (buttonY + buttonHeight).toFloat())
                canvas.drawRoundRect(buttonRect, 20f, 20f, buttonPaint)
                canvas.drawText("Сыграть еще раз?", buttonRect.centerX(), buttonRect.centerY() + 20f, buttonTextPaint)
            }

            val scoreTextPaint = Paint().apply {
                color = black
                textSize = 70f
                textAlign = Paint.Align.CENTER
            }
            canvas.drawText("Результат: $score", (canvas.width / 2).toFloat(), (canvas.height / 10).toFloat(), scoreTextPaint)

            // Отображение лучшего результата
            canvas.drawText("Лучший результат: $bestScore", (canvas.width / 2).toFloat(), (canvas.height / 5).toFloat(), scoreTextPaint)

            if (dynamicScoreDisplay.isNotEmpty()) {
                dynamicScorePaint.alpha = (255 * dynamicScoreAlpha).toInt()
                canvas.drawText(dynamicScoreDisplay, (canvas.width / 2).toFloat(), (canvas.height / 2 + dynamicScoreY).toFloat(), dynamicScorePaint)
            }
        }
    }

    private fun resetZoneAndSpeed() {
        startAngle = (0..(360 - arcLength)).random()
        speed = speedOptions.random()
        arcLength = arcLengthRange.random()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            if (gameOver) {
                // Проверка попадания нажатия на область кнопки "Сыграть еще раз?"
                if (buttonRect.contains(event.x, event.y)) {
                    restartGame()
                }
            } else {
                checkClick(surfaceView.width / 2, surfaceView.height / 2, angle, startAngle, arcLength)
            }
        }
        return super.onTouchEvent(event)
    }

    private fun restartGame() {
        score = 0
        gameOver = false
        angle = 0.0
        speed = speedOptions.random()
        arcLength = arcLengthRange.random()
        startAngle = (0..(360 - arcLength)).random()
    }

    private fun checkClick(centerX: Int, centerY: Int, angle: Double, startAngle: Int, arcLength: Int) {
        var currentAngle = angle % 360
        var startAngle = startAngle % 360
        var endAngle = (startAngle + arcLength) % 360

        val isSuccessfulClick: Boolean

        if (startAngle < endAngle) {
            isSuccessfulClick = startAngle <= currentAngle && currentAngle <= endAngle
        } else {
            isSuccessfulClick = currentAngle >= startAngle || currentAngle <= endAngle
        }

        if (isSuccessfulClick) {
            score++
            playSound()
            if (score > bestScore) {
                bestScore = score
                saveBestScore(bestScore)
            }
            resetZoneAndSpeed()
        } else {
            gameOver = true
        }
    }

    private fun playSound() {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, R.raw.marimba_odinochnyiy)
        mediaPlayer?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun saveBestScore(bestScore: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("bestScore", bestScore)
        editor.apply()
    }
}
