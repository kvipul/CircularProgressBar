package com.sablania.myprogressbar.customViews

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.sablania.myprogressbar.R
import kotlin.math.PI
import kotlin.math.abs
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
    private val padding = 20f

    var progress: Int = 0
        set(value) {
            field = value
            invalidate()
        }

    init {
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

        //draw grey circle
        paint.color = ContextCompat.getColor(context, R.color.grey)
        paint.style = Paint.Style.STROKE
        canvas?.drawCircle(cx, cy, actRadius, paint)

        //draw green arc
        paint.color = ContextCompat.getColor(context, R.color.green)
        paint.style = Paint.Style.FILL
        val oval = RectF()
        paint.style = Paint.Style.STROKE
        oval[padding, padding, diameter - padding] = diameter - padding
        val sweepAngle = progress * 360f / 100
        val sweepAngleRadian = (sweepAngle * PI / 180).toFloat()
        canvas?.drawArc(oval, 270f, sweepAngle, false, paint)


        //draw orange blob
        paint.color = ContextCompat.getColor(context, R.color.orange)
        paint.style = Paint.Style.FILL
        canvas?.drawCircle(
            cx + actRadius * sin(sweepAngleRadian),
            cy - actRadius * cos(sweepAngleRadian),
            blobRadius,
            paint
        )

        //draw text
        paint.textAlign = Paint.Align.CENTER
        paint.color = ContextCompat.getColor(context, R.color.grey)
        paint.textSize = diameter / 3
        canvas?.drawText("" + progress, cx, cy + paint.textSize / 3, paint)
    }

    fun animateProgress(progress: Int) {
        val valueAnimator = ValueAnimator.ofInt(this.progress, progress)
        val speed = 20
        valueAnimator.duration =
            speed * (abs(this.progress - progress)).toLong() // animation duration
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