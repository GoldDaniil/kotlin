package com.example.myapplication

import android.graphics.*
import android.os.Bundle
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.*

class LoopGameActivity : AppCompatActivity() {

    private lateinit var surfaceView: SurfaceView
    private lateinit var surfaceHolder: SurfaceHolder
    private lateinit var drawingThread: DrawingThread

    private val width = 600
    private val height = 600
    private val circleRadius = 200
    private val circleThickness = 5

    private val gray = Color.rgb(169, 169, 169)
    private val beige = Color.rgb(245, 245, 220)
    private val darkBeige = Color.rgb(222, 184, 135)
    private val green = Color.rgb(0, 255, 0)
    private val red = Color.rgb(255, 0, 0)
    private val black = Color.rgb(0, 0, 0)

    private val speedOptions = listOf(360 / 5.0, 360 / 4.0, 360 / 3.5, 360 / 3.0, 360 / 2.5)
    private val arcLengthRange = IntRange(10, 60)

    private var angle = 0.0
    private var speed = speedOptions.random()
    private var arcLength = arcLengthRange.random()
    private var score = 0
    private var gameOver = false
    private var startAngle = (0..(360 - arcLength)).random()
    private val FPS = 60 // константа для количества кадров в секунду

    private lateinit var icon: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loop_game)

        val iconDrawable = resources.getDrawable(R.drawable.helmet_icon, null)
        icon = Bitmap.createBitmap(iconDrawable.intrinsicWidth, iconDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(icon)
        iconDrawable.setBounds(0, 0, canvas.width, canvas.height)
        iconDrawable.draw(canvas)

        surfaceView = findViewById(R.id.surfaceView)
        surfaceHolder = surfaceView.holder

        drawingThread = DrawingThread()
        drawingThread.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        drawingThread.stopThread()
    }

    private inner class DrawingThread : Thread() {
        private var running = false

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
                val centerX = width / 2
                val centerY = height / 2

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

                val trianglePoints = mutableListOf<PointF>()
                trianglePoints.add(PointF(centerX.toFloat(), centerY.toFloat()))

                val startX = centerX + (outerRadius * cos(startAngleRad)).toFloat()
                val startY = centerY - (outerRadius * sin(startAngleRad)).toFloat()
                trianglePoints.add(PointF(startX, startY))

                val endX = centerX + (outerRadius * cos(endAngleRad)).toFloat()
                val endY = centerY - (outerRadius * sin(endAngleRad)).toFloat()
                trianglePoints.add(PointF(endX, endY))

                path.moveTo(trianglePoints[0].x, trianglePoints[0].y)
                path.lineTo(trianglePoints[1].x, trianglePoints[1].y)
                path.lineTo(trianglePoints[2].x, trianglePoints[2].y)
                path.lineTo(trianglePoints[0].x, trianglePoints[0].y)
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
            } else {
                val textPaint = Paint().apply {
                    color = red
                    textSize = 75f
                    textAlign = Paint.Align.CENTER
                }
                canvas.drawText("ХА! лох", (width / 2).toFloat(), (height / 2).toFloat(), textPaint)
            }

            val scoreTextPaint = Paint().apply {
                color = black
                textSize = 55f
            }
            canvas.drawText("Результат: $score", 10f, 10f + scoreTextPaint.textSize, scoreTextPaint)
        }
    }

    private fun resetZoneAndSpeed() {
        startAngle = (0..(360 - arcLength)).random()
        speed = speedOptions.random()
        arcLength = arcLengthRange.random()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN && !gameOver) {
            checkClick(width / 2, height / 2, angle, startAngle, arcLength)
        }
        return super.onTouchEvent(event)
    }

    private fun checkClick(centerX: Int, centerY: Int, angle: Double, startAngle: Int, arcLength: Int) {
        var currentAngle = angle % 360
        var startAngle = startAngle % 360
        var endAngle = (startAngle + arcLength) % 360

        if (startAngle < endAngle) {
            if (startAngle <= currentAngle && currentAngle <= endAngle) {
                score++
                resetZoneAndSpeed()
            } else {
                gameOver = true
            }
        } else {
            if (currentAngle >= startAngle || currentAngle <= endAngle) {
                score++
                resetZoneAndSpeed()
            } else {
                gameOver = true
            }
        }
    }
}

