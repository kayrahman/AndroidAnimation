/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nkr.androidanimation.customView.ProgressCircle.ExampleJava

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
import android.view.animation.Interpolator
import com.nkr.androidanimation.R

internal class ProgressCircleView : View {

    private var mInterpolator: Interpolator? = null
    private var mStartValue = 0
    private var mCurrentValue = 0
    private var mEndValue = 0

    private var mStrokeWidth = 0f
    private var mAnimationDuration = 0
    private var mBackCircleColor = 0
    private var mForegroundCircleColor = 0
    private var mAnimateOnDisplay = false
    private var mAnimationSpeed = 0f
    private var mBackCirclePaint: Paint? = null
    private var mForegroundCirclePaint: Paint? = null
    private var mCurrentAngle = 0f
    private var mEndAngle = 0
    private var mAnimationStartTime: Long = 0

    private var mListener: OnCircleAnimationListener? = null

    constructor(context: Context?) : super(context) {
        mInterpolator = AccelerateDecelerateInterpolator()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
    }

    fun init(context: Context, attrs: AttributeSet) {
        mInterpolator = AccelerateDecelerateInterpolator()

        readAttributesAndSetupFields(context, attrs)

        setupPaint()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mAnimationStartTime == 0L) {
            mAnimationStartTime = System.currentTimeMillis()
        }
        canvas.drawCircle(
            width / 2.toFloat(),
            height / 2.toFloat(),
            width / 2 - mStrokeWidth / 2,
            mBackCirclePaint!!
        )
        canvas.drawArc(
            0 + mStrokeWidth / 2,
            0 + mStrokeWidth / 2,
            width - mStrokeWidth / 2,
            height - mStrokeWidth / 2, -90f,
            if (mAnimateOnDisplay) currentFrameAngle else mEndAngle.toFloat(),
            false,
            mForegroundCirclePaint!!
        )
        if (mAnimateOnDisplay && mCurrentAngle < mEndAngle) {
            invalidate()
        }
    }

    fun showAnimation() {
        mAnimateOnDisplay = true
        mCurrentAngle = 0f
        mAnimationStartTime = 0
        invalidate()
    }

    private fun readAttributesAndSetupFields(
        context: Context,
        attrs: AttributeSet
    ) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ProgressCircleLayout,
            0, 0
        )
        try {
            applyAttributes(context, a)
            setEndAngle()
            setAnimationSpeed()
            log()
        } finally {
            a.recycle()
        }
    }

    private fun applyAttributes(context: Context, a: TypedArray) {
        mStartValue = a.getInt(R.styleable.ProgressCircleLayout_startValue, 0)
        mCurrentValue = a.getInt(R.styleable.ProgressCircleLayout_currentValue, 0)
        mEndValue = a.getInt(R.styleable.ProgressCircleLayout_endValue, 0)

        mAnimateOnDisplay = a.getBoolean(R.styleable.ProgressCircleLayout_animateOnDisplay, true)
        mAnimationDuration = a.getInt(
            R.styleable.ProgressCircleLayout_animationDuration,
            DEFAULT_ANIMATION_TIME
        )

        readBackCircleColorFromAttributes(a)
        readForegroundColorFromAttributes(context, a)

        mStrokeWidth = a.getDimension(
            R.styleable.ProgressCircleLayout_strokeWidth,
            getDefaultStrokeWidth(context).toFloat()
        )
    }

    private fun readForegroundColorFromAttributes(
        context: Context,
        a: TypedArray
    ) {
        val fc =
            a.getColorStateList(R.styleable.ProgressCircleLayout_foregroundCircleColor)

        mForegroundCircleColor = fc?.defaultColor ?: getAccentColor(context)
    }

    private fun readBackCircleColorFromAttributes(a: TypedArray) {
        val bc =
            a.getColorStateList(R.styleable.ProgressCircleLayout_backgroundCircleColor)

        mBackCircleColor = bc?.defaultColor ?: Color.parseColor("16000000")
    }

    private fun setAnimationSpeed() {
        val seconds = mAnimationDuration.toFloat() / 1000
        val i = (seconds * 60).toInt()
        mAnimationSpeed = mEndAngle.toFloat() / i
    }

    private fun setEndAngle() {
        val totalLength = mEndValue - mStartValue
        val pathGone = mCurrentValue - mStartValue
        val v = pathGone.toFloat() / totalLength
        mEndAngle = (360 * v).toInt()
    }

    private fun log() {
        Log.d(
            LOG_TAG,
            "readAttributesAndSetupFields: start value $mStartValue"
        )
        Log.d(
            LOG_TAG,
            "readAttributesAndSetupFields: current value $mCurrentValue"
        )
        Log.d(
            LOG_TAG,
            "readAttributesAndSetupFields: end value $mEndValue"
        )
        Log.d(
            LOG_TAG,
            "readAttributesAndSetupFields: end angle $mEndAngle"
        )
        Log.d(
            LOG_TAG,
            "readAttributesAndSetupFields: animation speed $mAnimationSpeed"
        )
        Log.d(
            LOG_TAG,
            "readAttributesAndSetupFields: animation time $mAnimationDuration"
        )
    }

    private fun setupPaint() {
        setupBackCirclePaint()
        setupFrontCirclePaint()
    }

    private fun setupFrontCirclePaint() {
        mForegroundCirclePaint = Paint()
        mForegroundCirclePaint!!.color = mForegroundCircleColor
        mForegroundCirclePaint!!.style = Paint.Style.STROKE
        mForegroundCirclePaint!!.strokeCap = Paint.Cap.ROUND
        mForegroundCirclePaint!!.strokeWidth = mStrokeWidth
    }

    private fun setupBackCirclePaint() {
        mBackCirclePaint = Paint()
        mBackCirclePaint!!.color = mBackCircleColor
        mBackCirclePaint!!.style = Paint.Style.STROKE
        mBackCirclePaint!!.strokeWidth = mStrokeWidth
    }

    private val currentFrameAngle: Float
        private get() {
            val now = System.currentTimeMillis()
            val pathGone =
                (now - mAnimationStartTime).toFloat() / mAnimationDuration
            val interpolatedPathGone = mInterpolator!!.getInterpolation(pathGone)
            if (pathGone < 1.0f) {
                mCurrentAngle = mEndAngle * interpolatedPathGone
                mListener?.onCircleAnimation(getCurrentAnimationFrameValue(interpolatedPathGone))
            } else {
                mCurrentAngle = mEndAngle.toFloat()
                mListener?.onCircleAnimation(getCurrentAnimationFrameValue(1.0f))
            }
            return mCurrentAngle
        }

    fun setOnCircleAnimationListener(l: OnCircleAnimationListener) {
        mListener = l
    }

    private fun getAccentColor(context: Context): Int {
        val typedValue = TypedValue()
        val a =
            context.obtainStyledAttributes(typedValue.data, intArrayOf(R.attr.colorAccent))
        val color = a.getColor(0, 0)
        a.recycle()
        return color
    }

    private fun getDefaultStrokeWidth(context: Context): Int {
        return (context.resources.displayMetrics.density * 10).toInt()
    }

    fun setInterpolator(i: Interpolator?) {
        mInterpolator = i
    }

    fun getCurrentAnimationFrameValue(interpolatedPathGone: Float): String {
        val value =
            Math.round((mCurrentValue - mStartValue) * interpolatedPathGone) + mStartValue
        return value.toString()
    }

    companion object {
        private const val LOG_TAG = "ProgressCircleView"
        private const val DEFAULT_ANIMATION_TIME = 1000
    }
}
