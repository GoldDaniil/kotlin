package com.example.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class CircleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val outerPaint: Paint = Paint()
    private val innerPaint: Paint = Paint()
    private val borderPaint: Paint = Paint()

    private var outerDiameter = 0
    private var innerDiameter = 0
    private var borderWidth = 0

    init {
        outerPaint.color = ContextCompat.getColor(context, android.R.color.transparent)
        innerPaint.color = ContextCompat.getColor(context, android.R.color.white)
        borderPaint.color = ContextCompat.getColor(context, android.R.color.holo_red_light)

        outerPaint.style = Paint.Style.FILL
        innerPaint.style = Paint.Style.FILL
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = resources.displayMetrics.density * 2

        val screenWidth = resources.displayMetrics.widthPixels
        outerDiameter = (screenWidth * 2) / 3
        innerDiameter = (screenWidth * 2) / 3 - (screenWidth / 5)
        borderWidth = (screenWidth / 20)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(outerDiameter, outerDiameter)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val outerRadius = outerDiameter / 2f
        val innerRadius = innerDiameter / 2f
        val borderWidthHalf = borderWidth / 2f

        canvas.drawCircle(outerRadius, outerRadius, outerRadius - borderWidthHalf, outerPaint)
        canvas.drawCircle(outerRadius, outerRadius, outerRadius - borderWidthHalf, borderPaint)

        canvas.drawCircle(outerRadius, outerRadius, innerRadius, innerPaint)
    }
}
