package com.sablania.myprogressbar.customViews

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * a fix width-height should be given to view otherwise wrap content would take 0-0 width-height,
 * Although a min width-height can be set easily in this class
 */

class CustomProgressBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()
    private val blobRadius = 20f
    private val strokeWidth = 10f
    private val padding = 10f
    var progress: Int = 0
        set(value) {
            field = value
            invalidate()
        }

    init {
//        val paint = Paint()
        paint.strokeWidth = strokeWidth
    }

    override fun onDraw(canvas: Canvas?) {
        drawCircularBar(canvas)
        super.onDraw(canvas)
    }

    private fun drawCircularBar(canvas: Canvas?) {
        val h = this.height.toFloat()
        val w = this.width.toFloat()

        val diameter = if (h > w) w else h
        val radius = diameter / 2
        val actRadius = radius - padding
        val actDiameter = 2 * actRadius
        val cx = diameter / 2
        val cy = diameter / 2



        paint.color = Color.GRAY
        paint.style = Paint.Style.STROKE
        canvas?.drawCircle(cx, cy, actRadius, paint)

        paint.color = Color.GREEN
        paint.style = Paint.Style.FILL
        val oval = RectF()
        paint.style = Paint.Style.STROKE
        oval[padding, padding, diameter - padding] = diameter - padding
        val sweepAngle = progress * 360f / 100
        val sweepAngleRadian = (sweepAngle * PI / 180).toFloat()
        canvas?.drawArc(oval, 270f, sweepAngle, false, paint)


        paint.color = Color.YELLOW
        paint.style = Paint.Style.FILL
        canvas?.drawCircle(
            cx + actRadius * sin(sweepAngleRadian),
            cy - actRadius * cos(sweepAngleRadian),
            blobRadius,
            paint
        )

//        paint.setTextAlign(Paint.Align.CENTER)
//        paint.setColor(Color.parseColor("#8E8E93"))
//        paint.setTextSize(140f)
//        canvas?.drawText("" + progress, 150f, 150 + paint.getTextSize() / 3, paint)
    }

    private fun animateProgress(progress: Int) {
        val valueAnimator = ValueAnimator.ofInt(this.progress, progress)
        valueAnimator.duration = 2000 // animation duration
        valueAnimator.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(p0: ValueAnimator?) {
                p0?.let {
                    this@CustomProgressBar.progress = p0.animatedValue.toString().toInt()
                }
            }

        })
        valueAnimator.start()
    }

}