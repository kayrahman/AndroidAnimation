package com.nkr.androidanimation.customView.ProgressCircle

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.Interpolator
import com.nkr.androidanimation.R
import timber.log.Timber

class ProgressCircularView : View{

    private var mInterpolator: Interpolator? = null

    private var mStrokeWidth = 0f

    private var mStartValue = 0
    private var mCurrentValue = 0
    private var mEndValue = 0

    private var foregroundColor = 0
    private var backgroundProgressColor  = 0

    private var mAnimateOnDisplay = false
    private var mAnimationSpeed = 0f
    private var mCurrentAngle = 0f
    private var mEndAngle = 0
    private var mAnimationStartTime: Long = 0



    constructor(context: Context?) : super(context)

    constructor(context: Context?,attrs: AttributeSet?) :
            super(context,attrs) {


    }


    private val circleBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        // Paint styles used for rendering are initialized here. This
        // is a performance optimization, since onDraw() is called
        // for every screen refresh.
        style = Paint.Style.STROKE
        textAlign = Paint.Align.CENTER


    }



    fun init(context: Context, attrs: AttributeSet) {

        readAttributesAndSetupFields(context, attrs)

    }

    private fun readAttributesAndSetupFields(context: Context, attrs: AttributeSet) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ProgressCircleLayout,
            0, 0
        )
        try {
            applyAttributes(context, a)
            setEndAngle()
           // setAnimationSpeed()
           // log()
        } finally {
            a.recycle()
        }

    }

    private fun applyAttributes(context: Context, a: TypedArray) {

        mStartValue = a.getInt(R.styleable.ProgressCircleLayout_startValue, 0)
        mCurrentValue = a.getInt(R.styleable.ProgressCircleLayout_currentValue, 0)
        mEndValue = a.getInt(R.styleable.ProgressCircleLayout_endValue, 0)


        val bc = a.getColorStateList(R.styleable.ProgressCircleLayout_backgroundCircleColor)
        backgroundProgressColor = bc?.defaultColor ?: Color.parseColor("16000000")



       val fc = a.getColorStateList(R.styleable.ProgressCircleLayout_foregroundCircleColor)
        foregroundColor = fc?.defaultColor ?: getAccentColor(context)


        mStrokeWidth = a.getDimension(
            R.styleable.ProgressCircleLayout_strokeWidth,
            getDefaultStrokeWidth(context).toFloat())


        Log.d("stroke_width",mStrokeWidth.toString())




    }

    private fun getAccentColor(context: Context): Int {
        val typedValue = TypedValue()
        val a =
            context.obtainStyledAttributes(typedValue.data, intArrayOf(R.attr.colorAccent))
        val color = a.getColor(0, 0)
        a.recycle()
        return color
    }

    private val circleForegroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND

        }



    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (mAnimationStartTime == 0L) {
            mAnimationStartTime = System.currentTimeMillis()
        }

        circleForegroundPaint.strokeWidth = mStrokeWidth
        circleBackgroundPaint.strokeWidth = mStrokeWidth


        circleBackgroundPaint.color = backgroundProgressColor

        canvas?.drawCircle(width/2.toFloat(),height/2.toFloat(),
            width/2.toFloat() - mStrokeWidth*2 ,circleBackgroundPaint)

        circleForegroundPaint.color = foregroundColor

        canvas?.drawArc(0f+mStrokeWidth*2,0f+mStrokeWidth*2,
            width.toFloat()-mStrokeWidth*2
            ,height.toFloat()-mStrokeWidth*2,
            -90f,
            currentFrameAngle,
            false,
            circleForegroundPaint

        )

        Log.d("current_frame_angle",currentFrameAngle.toString())


        if ( mCurrentAngle < mEndAngle) {
            invalidate()
        }

    }


    fun showAnimation() {
        mAnimateOnDisplay = true
        mCurrentAngle = 0f
        mAnimationStartTime = 0
        invalidate()
    }


    private fun setEndAngle() {
        val totalLength = mEndValue - mStartValue
        val pathGone = mCurrentValue - mStartValue
        val v = pathGone.toFloat() / totalLength
        mEndAngle = (360 * v).toInt()
    }


    private val currentFrameAngle: Float
        private get() {

            val now = System.currentTimeMillis()
            val pathGone =
                (now - mAnimationStartTime).toFloat() / 1000

            Log.d("path_gone",pathGone.toString())

            mInterpolator = AccelerateInterpolator()

            val interpolatedPathGone = mInterpolator!!.getInterpolation(pathGone)

            if (pathGone < 1.0f) {
                mCurrentAngle = mEndAngle * interpolatedPathGone

               // mListener?.onCircleAnimation(getCurrentAnimationFrameValue(interpolatedPathGone))
            } else {
                mCurrentAngle = mEndAngle.toFloat()
               // mListener?.onCircleAnimation(getCurrentAnimationFrameValue(1.0f))
            }
            return mCurrentAngle
        }



    private fun getDefaultStrokeWidth(context: Context): Int {
        return (context.resources.displayMetrics.density * 10).toInt()
    }


}