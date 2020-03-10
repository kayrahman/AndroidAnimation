package com.nkr.androidanimation.customView

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator.INFINITE
import android.animation.ValueAnimator.RESTART
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.drawable.Drawable
import android.util.FloatProperty
import android.util.Log
import android.view.animation.LinearInterpolator
import kotlin.math.PI

class PolygonLapsDrawable : Drawable(){

    init {

      val lineProgressAnimator =  ObjectAnimator.ofFloat(this, PROGRESS, 0f, 1f).apply {
            duration = 6000L
            interpolator = LinearInterpolator()
            //repeatCount = 1
          //  repeatMode = RESTART
        }
          //.start()


        val dotProgressAnimator =  ObjectAnimator.ofFloat(this, DOT_PROGRESS, 0f, 1f).apply {
            duration = 12000L
            interpolator = LinearInterpolator()
            repeatCount = INFINITE
            repeatMode = RESTART
        }
            //.start()


        val animatorSet = AnimatorSet()
        animatorSet.play(lineProgressAnimator).before(dotProgressAnimator)
       // animatorSet.playTogether(lineProgressAnimator,dotProgressAnimator)
        animatorSet.start()


    }

    var progress = 1f
        set(value) {
            field = value.coerceIn(0f, 1f)
            callback?.invalidateDrawable(this)
        }

    var dotProgress = 0f
        set(value) {
            field = value.coerceIn(0f, 1f)
            callback?.invalidateDrawable(this)
        }


    private val polygons = listOf(
        Polygon(15, 0xffe84c65.toInt(), 324f, 2),
        Polygon(14, 0xffe84c65.toInt(), 300f, 3),
        Polygon(13, 0xffd554d9.toInt(), 276f, 4),
        Polygon(12, 0xffaf6eee.toInt(), 248f, 5),
        Polygon(11, 0xff4a4ae6.toInt(), 224f, 6),
        Polygon(10, 0xff4294e7.toInt(), 200f, 7),
        Polygon(9, 0xff6beeee.toInt(), 176f, 8),
        Polygon(8, 0xff42e794.toInt(), 148f, 9),
        Polygon(7, 0xff5ae75a.toInt(), 124f, 10),
        Polygon(6, 0xffade76b.toInt(), 100f, 11),
        Polygon(5, 0xffefefbb.toInt(), 76f, 12),
        Polygon(4, 0xffe79442.toInt(), 48f, 13),
        Polygon(3, 0xffe84c65.toInt(), 24f, 14)
    )

    private val cornerEffect = CornerPathEffect(4f)

    private val linePaint = Paint(ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 4f
        pathEffect = cornerEffect
    }

    private val dotPaint = Paint(ANTI_ALIAS_FLAG).apply {
        //color = 0xff0e0d0e.toInt()
        color = 0xffe5e5e5.toInt()
        style = Paint.Style.FILL
    }

    private val pathDot = Path().apply {
        addCircle(0f, 0f, 8f, Path.Direction.CW)
    }



    override fun draw(canvas: Canvas) {


        polygons.forEach { polygon ->
            linePaint.color = polygon.color

            if (progress < 1f) {
                val progressEffect = DashPathEffect(
                    floatArrayOf(0f, (1f - progress) * polygon.length, progress * polygon.length, 0f),
                    polygon.initialPhase)
                linePaint.pathEffect = ComposePathEffect(progressEffect, cornerEffect)
            }

            canvas.drawPath(polygon.path, linePaint)
        }


        // loop separately to ensure the dots are on top
        polygons.forEach { polygon ->
            //advance
            val phase = polygon.initialPhase + dotProgress * polygon.length * polygon.laps

            dotPaint.pathEffect = PathDashPathEffect(pathDot, polygon.length, phase, PathDashPathEffect.Style.TRANSLATE)
            Log.d("poly_length",polygon.length.toString())
            Log.d("poly_phase",polygon.initialPhase.toString())
            canvas.drawPath(polygon.path, dotPaint)
        }


    }

    override fun setAlpha(alpha: Int) {

        linePaint.alpha = alpha

    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }



    override fun setColorFilter(colorFilter: ColorFilter?) {
        linePaint.colorFilter = colorFilter
    }


    override fun getIntrinsicWidth() = width

    override fun getIntrinsicHeight() = height


companion object {
    private const val width = 350
    private const val height = 350
    private const val cx = (width).toFloat()
    private const val cy = (height).toFloat()
    private val pathMeasure = PathMeasure()

}
    private class Polygon(val sides: Int, val color: Int, radius: Float, val laps: Int) {


        val path = createPath(sides, radius)

        val length by lazy(LazyThreadSafetyMode.NONE) {
            pathMeasure.setPath(path, false)
            pathMeasure.length

        }


        val initialPhase by lazy(LazyThreadSafetyMode.NONE) {
            (1f - (1f / (2 * sides))) * length
        }

        private fun createPath(sides: Int, radius: Float): Path {
            val path = Path()
            val angle = 2.0 * PI / sides
            val startAngle = PI / 2.0 + Math.toRadians(360.0 / (2 * sides))


            path.moveTo(
                cx + (radius * Math.cos(startAngle)).toFloat(),
                cy + (radius * Math.sin(startAngle)).toFloat()
            )

            for (i in 1 until sides) {
                path.lineTo(
                    cx + (radius * Math.cos(startAngle - angle * i)).toFloat(),
                    cy + (radius * Math.sin(startAngle - angle * i)).toFloat()
                )
            }
            path.close()
            return path
        }
    }


    object PROGRESS : FloatProperty<PolygonLapsDrawable>("progress") {
        override fun setValue(drawable: PolygonLapsDrawable, progress: Float) {
            drawable.progress = progress
        }

        override fun get(drawable: PolygonLapsDrawable) = drawable.progress
    }

    object DOT_PROGRESS : FloatProperty<PolygonLapsDrawable>("dotProgress") {
        override fun setValue(drawable: PolygonLapsDrawable, dotProgress: Float) {
            drawable.dotProgress = dotProgress
        }

        override fun get(drawable: PolygonLapsDrawable) = drawable.dotProgress
    }


}










































