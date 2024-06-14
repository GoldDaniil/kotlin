package com.example.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class CircleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val outerPaint: Paint = Paint()
    private val innerPaint: Paint = Paint()
    private val borderPaint: Paint = Paint()
    private val greenPaint: Paint = Paint()

    private var outerDiameter = 0
    private var innerDiameter = 0
    private var borderWidth = 0

    private var currentTime = 0L // текущее время для анимации
    private val animationDuration = 3000L // длительность одного оборота в миллисекундах
    private val iconSize = 50 // размер иконки в пикселях
    private var centerX = 0f // координата X центра окружности
    private var centerY = 0f // координата Y центра окружности
    private var radiusRed = 0f // радиус красной окружности
    private var radiusWhite = 0f // радиус белой окружности

    private var helmetIcon: Drawable? = null

    private var greenArcStartAngle = 0f // начальный угол для зеленого сегмента
    private var greenArcSweepAngle = 0f // длина (угол) зеленого сегмента
    private var maxGreenArcLength = 240f // максимальная длина (угол) зеленого сегмента

    init {
        outerPaint.color = ContextCompat.getColor(context, android.R.color.transparent)
        innerPaint.color = ContextCompat.getColor(context, android.R.color.white)
        borderPaint.color = ContextCompat.getColor(context, android.R.color.holo_red_light)
        greenPaint.color = ContextCompat.getColor(context, android.R.color.holo_green_light)

        outerPaint.style = Paint.Style.FILL
        innerPaint.style = Paint.Style.FILL
        borderPaint.style = Paint.Style.STROKE

        helmetIcon = ContextCompat.getDrawable(context, R.drawable.helmet_icon)

        val screenWidth = resources.displayMetrics.widthPixels
        outerDiameter = (screenWidth * 2) / 3
        innerDiameter = (screenWidth * 2) / 3 - (screenWidth / 5)
        borderWidth = (screenWidth / 15)
        borderPaint.strokeWidth = borderWidth.toFloat()

        centerX = (screenWidth - outerDiameter) / 2f + outerDiameter / 2f
        centerY = (screenWidth - outerDiameter) / 2f + outerDiameter / 2f
        radiusRed = outerDiameter / 2f - borderWidth / 2f
        radiusWhite = innerDiameter / 2f

        startAnimation()
    }

    private fun startAnimation() {
        val startTime = System.currentTimeMillis()
        post(object : Runnable {
            override fun run() {
                val elapsedTime = System.currentTimeMillis() - startTime
                updateAnimation(elapsedTime.toFloat() / animationDuration.toFloat())
                invalidate()

                if (System.currentTimeMillis() - startTime < animationDuration) {
                    postDelayed(this, 16)
                } else {
                    updateGreenArc()
                    startAnimation()
                }
            }
        })
    }

    private fun updateAnimation(elapsedFraction: Float) {
        currentTime = (elapsedFraction * animationDuration).toLong()

        currentTime %= animationDuration
    }

    private fun updateGreenArc() {
        greenArcStartAngle = Random.nextFloat() * 360f
        greenArcSweepAngle = Random.nextFloat() * maxGreenArcLength

        if (greenArcSweepAngle > maxGreenArcLength) {
            greenArcSweepAngle = maxGreenArcLength
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawCircle(centerX, centerY, radiusRed, outerPaint)
        canvas.drawCircle(centerX, centerY, radiusRed, borderPaint)

        canvas.drawCircle(centerX, centerY, radiusWhite, innerPaint)

        val rect = RectF(centerX - radiusRed, centerY - radiusRed, centerX + radiusRed, centerY + radiusRed)
        canvas.drawArc(rect, greenArcStartAngle, greenArcSweepAngle, true, greenPaint)

        val angle = 360f * (currentTime.toFloat() / animationDuration.toFloat())
        val iconX = centerX + radiusRed * cos(Math.toRadians(angle.toDouble())).toFloat()
        val iconY = centerY + radiusRed * sin(Math.toRadians(angle.toDouble())).toFloat()

        helmetIcon?.let {
            val iconLeft = iconX - iconSize / 2
            val iconTop = iconY - iconSize / 2
            val iconRight = iconX + iconSize / 2
            val iconBottom = iconY + iconSize / 2
            it.setBounds(iconLeft.toInt(), iconTop.toInt(), iconRight.toInt(), iconBottom.toInt())
            it.draw(canvas)
        }
    }
}
