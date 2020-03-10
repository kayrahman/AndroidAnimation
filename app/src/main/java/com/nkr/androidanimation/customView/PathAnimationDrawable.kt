package com.nkr.androidanimation.customView

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.FloatProperty
import android.util.Log
import android.view.animation.LinearInterpolator
import kotlin.math.PI
import kotlin.random.Random

class PathAnimationDrawable : Drawable(){

    var progress = 1f
        set(value) {
            field = value.coerceIn(0f, 1f)
            callback?.invalidateDrawable(this)
        }



    init {

        val lineProgressAnimator =  ObjectAnimator.ofFloat(this, PROGRESS, 0f, 1f).apply {
            duration = 6000L
            interpolator = LinearInterpolator()

        }
        .start()

    }


    private val randomPaths = listOf(
        RandomPath( 0xffe84c65.toInt(),3),
        RandomPath( 0xffd554d9.toInt(),3),
        RandomPath( 0xffaf6eee.toInt(),3),
        RandomPath( 0xff4a4ae6.toInt(),3),
        RandomPath( 0xff4294e7.toInt(),3)

    )


    override fun draw(canvas: Canvas) {

        randomPaths.forEach {it
            linePaint.color = it.color

            canvas.drawPath(it.path, linePaint)
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

    private val cornerEffect = CornerPathEffect(4f)

    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 8f
        pathEffect = cornerEffect
    }

    override fun getIntrinsicWidth() = width

    override fun getIntrinsicHeight() = height

    companion object {
        private const val width = 350
        private const val height = 650
        private const val cx = (width).toFloat()
        private const val cy = (height).toFloat()
        private val pathMeasure = PathMeasure()

    }

    private class RandomPath(val color : Int,val sides:Int){

        val randomPathLength = Random.nextInt(50,150)

        val path = createRandomPaths(randomPathLength.toFloat(),color)


        private fun createRandomPaths(path_length : Float, color: Int): Path{
            val path = Path()

            val angle =  PI

            Log.d("angle",angle.toString())

            val startAngle = PI + Math.toRadians(360.0 / 2)
            Log.d("startangle",angle.toString())

            val randomXpostion = Random.nextInt(100, width)
            val randomYpostion = Random.nextInt(100, height)

            path.moveTo(randomXpostion + (path_length),
                randomYpostion + (path_length).toFloat()
                )

            for (i in 1 until sides) {
                path.lineTo(
                   // randomXpostion + (path_length * Math.cos(startAngle - angle * i)).toFloat(),
                    //randomYpostion + (path_length * Math.sin(startAngle - angle * i)).toFloat()

                    randomXpostion + (path_length * Math.cos(startAngle * i )).toFloat(),
                    randomYpostion + (path_length * Math.sin(startAngle * i )).toFloat()

                )
            }



/*

            path.moveTo(
                PolygonLapsDrawable.cx + (radius * Math.cos(startAngle)).toFloat(),
                PolygonLapsDrawable.cy + (radius * Math.sin(startAngle)).toFloat()
            )

            for (i in 1 until sides) {
                path.lineTo(
                    PolygonLapsDrawable.cx + (radius * Math.cos(startAngle - angle * i)).toFloat(),
                    PolygonLapsDrawable.cy + (radius * Math.sin(startAngle - angle * i)).toFloat()
                )
            }
*/


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