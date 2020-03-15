package com.nkr.androidanimation.customView

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.FloatProperty
import android.util.Half.toFloat
import android.util.Log
import android.view.animation.LinearInterpolator
import com.nkr.androidanimation.R
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.random.Random


class CircleAnimationDrawable : Drawable() {

    private val pointPosition: PointF = PointF(0.0f, 0.0f)



    private fun PointF.computeXYForSpeed(circle_num : Int, radius: Float) {


            // Angles are in radians.
            val startAngle = Math.PI * (9 / 8.0)
            val angle = startAngle + circle_num * (Math.PI / 8)



          //  val angle = 2.0 * PI / (circle_num  )
           // val startAngle = PI / 2.0 + Math.toRadians(360.0 / (4 * circle_num))

            x = (radius * cos(angle)).toFloat() + 400
            y = (radius * sin(angle)).toFloat() + 400



    }



    override fun draw(canvas: Canvas) {

        // Draw the indicator circle.
        val markerRadius = 200f
        val circleRadius = 50f
        circlePaint.color = 0xffd554d9.toInt()

        for (i in 1 until 20) {
            pointPosition.computeXYForSpeed(i, markerRadius)
            canvas.drawCircle(pointPosition.x, pointPosition.y, circleRadius, circlePaint)
        }



    }

    override fun setAlpha(alpha: Int) {
        circlePaint.alpha = alpha

    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        circlePaint.colorFilter = colorFilter
    }

    private val cornerEffect = CornerPathEffect(4f)

    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 6f
        pathEffect = cornerEffect
    }




}