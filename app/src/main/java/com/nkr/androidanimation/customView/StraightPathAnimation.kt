package com.nkr.androidanimation.customView

import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.Log


class StraightPathAnimation : Drawable(){

    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 12f
        color = Color.LTGRAY
        pathEffect = cornerEffect
        strokeCap = Paint.Cap.ROUND

    }

    private val dotPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        //color = 0xff0e0d0e.toInt()
      //  color = 0xffe5e5e5.toInt()

        color = Color.YELLOW
        style = Paint.Style.FILL

    }

    private val cornerEffect = CornerPathEffect(0f)

    private val pathDot = Path().apply {
        addCircle(0f, 0f, 16f, Path.Direction.CW)
    }


    override fun draw(canvas: Canvas) {

        val straightPath = StraightPath()

        canvas.drawPath(straightPath.path,linePaint)

        val phase = straightPath.initialPhase + straightPath.length * 2
        dotPaint.pathEffect = PathDashPathEffect(pathDot,straightPath.length/2,phase,PathDashPathEffect.Style.TRANSLATE)

        Log.d("length_dot",phase.toString())
        Log.d("length_dot",straightPath.length.toString())


        canvas.drawPath(straightPath.path,dotPaint)

        val mPaint = Paint()
        mPaint.style = Paint.Style.STROKE
        mPaint.isAntiAlias = true
        val dashPathEffect2 = DashPathEffect(floatArrayOf(5f, 5f), 0f)
        mPaint.pathEffect = dashPathEffect2
        mPaint.setStrokeWidth(30f)
        val radius = 200f
        val colors = intArrayOf(Color.GRAY, Color.WHITE)
        val positions = floatArrayOf(0.75f, 1f)
        val gradient1 = SweepGradient(400f, 800f, colors, positions)
        mPaint.shader = gradient1
        canvas.drawCircle(400f, 800f, radius, mPaint)


/*
        val p = Paint()
        p.color = Color.RED
        val dashPath = DashPathEffect(floatArrayOf(5f, 5f), 1.0.toFloat())

        p.pathEffect = dashPath
        p.style = Paint.Style.STROKE
        canvas.drawCircle(100f, 100f, 50f, p)*/

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


    companion object {
        private val pathMeasure = PathMeasure()


    }


    private class StraightPath{

        val path = createPaths()


        val length by lazy(LazyThreadSafetyMode.NONE) {
            pathMeasure.setPath(path, false)
            pathMeasure.length

        }

        val initialPhase by lazy(LazyThreadSafetyMode.NONE) {
          //  (1f - (2f / (4 * 1))) * length

            length * .1f

        }

        private fun createPaths(): Path {

           val path = Path()

            path.moveTo(200f,200f)
            path.lineTo(400f,400f)

            path.close()

            return path

        }

    }



}