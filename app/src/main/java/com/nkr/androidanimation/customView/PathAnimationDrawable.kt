package com.nkr.androidanimation.customView

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.FloatProperty
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class PathAnimationDrawable : Drawable(){

    object DOT_PROGRESS : FloatProperty<PathAnimationDrawable>("dotProgress") {
        override fun setValue(drawable: PathAnimationDrawable, dotProgress: Float) {
            drawable.dotProgress = dotProgress
        }

        override fun get(drawable: PathAnimationDrawable) = drawable.dotProgress
    }


    init{

        val dotProgressAnimator =  ObjectAnimator.ofFloat(this, PathAnimationDrawable.DOT_PROGRESS, 0f, 1f).apply {
            duration = 6000L
            interpolator = AccelerateDecelerateInterpolator()
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
        }
        .start()

    }

    val c = arrayOf(0xffe84c65.toInt(), 0xffe84c65.toInt(), 0xffd554d9.toInt(),
        0xffaf6eee.toInt(), 0xff4a4ae6.toInt(), 0xff4294e7.toInt(),
        0xff6beeee.toInt(),0xff42e794.toInt(),
        0xff5ae75a.toInt(),0xffade76b.toInt(),
        0xffefefbb.toInt(),0xffe79442.toInt(),
        0xffe84c65.toInt()
        )


    private val pointPosition: PointF = PointF(0.0f, 0.0f)


    private fun PointF.computeXYForSpeed(circle_number:Int,radius: Float) {
        // Angles are in radians.
        // Angles are in radians.
        val startAngle = Math.PI * (9 / 8.0)
        val angle = startAngle + circle_number * (Math.PI / 4)

        //  val angle = 2.0 * PI / (circle_num  )
        // val startAngle = PI / 2.0 + Math.toRadians(360.0 / (4 * circle_num))

        x = (radius * cos(angle)).toFloat() + 340
        y = (radius * sin(angle)).toFloat() + 540


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



    private val pathDot = Path().apply {
        addCircle(0f, 0f, 12f, Path.Direction.CW)
    }


    override fun draw(canvas: Canvas) {
        for(i in 0 until 8){
            pointPosition.computeXYForSpeed(i,160f)
           val cx = pointPosition.x
           val cy = pointPosition.y

            val randomPath = RandomPath(cx,cy, c[i],4,50f)

           linePaint.color = randomPath.color
            canvas.drawPath(randomPath.path, linePaint)

            val phase = randomPath.initialPhase + dotProgress * randomPath.length * 2
            dotPaint.pathEffect = PathDashPathEffect(pathDot,randomPath.length ,phase,PathDashPathEffect.Style.TRANSLATE)

            Log.d("length_dot",phase.toString())
            Log.d("length_dot",randomPath.length.toString())

            canvas.drawPath(randomPath.path, dotPaint)
        }



    }

    override fun setAlpha(alpha: Int) {
        linePaint.alpha = alpha
        dotPaint.alpha = alpha

    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        linePaint.colorFilter = colorFilter
        dotPaint.colorFilter = colorFilter
    }

    private val cornerEffect = CornerPathEffect(60f)

    private val dotPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        //color = 0xff0e0d0e.toInt()
        color = 0xffe5e5e5.toInt()
        style = Paint.Style.FILL



    }

    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 8f
        pathEffect = cornerEffect
        strokeCap = Paint.Cap.ROUND

    }

    override fun getIntrinsicWidth() = width

    override fun getIntrinsicHeight() = height

    companion object {
        private const val width = 800
        private const val height = 800
        private val pathMeasure = PathMeasure()


    }

    private class RandomPath(val cx:Float,val cy:Float,val color : Int,val sides:Int,val radius : Float){

        val path = createRandomPaths(radius,color)

        val length by lazy(LazyThreadSafetyMode.NONE) {
            pathMeasure.setPath(path, false)
            pathMeasure.length

        }

        val initialPhase by lazy(LazyThreadSafetyMode.NONE) {
            (1f - (1f / (2 * sides))) * length
        }


        private fun createRandomPaths(radius : Float, color: Int): Path{

            Log.d("cx",cx.toString())

            val path = Path()

            val angle = 2 * PI/ sides
            val startAngle = PI/2 + Math.toRadians(360.0 / ( sides))

                path.moveTo(
                    cx + (radius * Math.cos(startAngle)).toFloat(),
                    cy + (radius * Math.sin(startAngle)).toFloat()
                )
                for (i in 1 until sides) {
                    path.lineTo(

                        cx+ (radius * Math.cos(startAngle - angle * i)).toFloat(),
                        cy + (radius * Math.sin(startAngle - angle * i)).toFloat()

                    )
                }


            path.close()
            return path

        }



    }

    object PROGRESS : FloatProperty<PathAnimationDrawable>("progress") {
        override fun setValue(drawable: PathAnimationDrawable, progress: Float) {
            drawable.progress = progress
        }

        override fun get(drawable: PathAnimationDrawable) = drawable.progress
    }





}