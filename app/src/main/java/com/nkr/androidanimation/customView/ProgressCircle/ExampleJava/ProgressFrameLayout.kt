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
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.Interpolator
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.nkr.androidanimation.R

class ProgressFrameLayout : FrameLayout {

    private var mCircle: ProgressCircleView? = null
    private var mValuesLayout: LinearLayout? = null
    private var mValue: TextView? = null
    private var mMetrics: TextView? = null

    constructor(context: Context?) : super(context!!) {}

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        Log.d(LOG_TAG, "ProgressCircleLayout: ")

        init(context, attrs)

        mCircle!!.init(context, attrs!!)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {

        init(context, attrs)

        mCircle!!.init(context, attrs!!)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
        mCircle!!.init(context, attrs!!)
    }

    private fun init(c: Context, attrs: AttributeSet?) {
        findViews()

        setOnCircleAnimationListener()

        setupValues(c, attrs)
    }

    private fun setOnCircleAnimationListener() {
        mCircle!!.setOnCircleAnimationListener(object : OnCircleAnimationListener {
            override fun onCircleAnimation(currentAnimationValue: String?) {
                if (mValue!!.text != currentAnimationValue) {
                    mValue!!.text = currentAnimationValue
                }
            }
        })
    }

    private fun findViews() {
       /*
        View.inflate(context, R.layout.progress_circle_layout, this)
        mCircle = findViewById<View>(R.id.circle) as ProgressCircleView
        mValuesLayout = findViewById<View>(R.id.values_layout) as LinearLayout
        mValue = findViewById<View>(R.id.value) as TextView
        mMetrics = findViewById<View>(R.id.metrics) as TextView*/
    }

    fun setInterpolator(i: Interpolator?) {
        mCircle!!.setInterpolator(i)
    }

    fun showAnimation() {
        mCircle!!.showAnimation()
    }

    fun setupValues(c: Context, attrs: AttributeSet?) {
        val a = c.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ProgressCircleLayout,
            0, 0
        )
        try {
            val metricsGravity = a.getInt(R.styleable.ProgressCircleLayout_metricsGravity, 0)
            val metrics = a.getString(R.styleable.ProgressCircleLayout_metrics)
            val value = a.getString(R.styleable.ProgressCircleLayout_currentValue)
            setupValues(metricsGravity, metrics, value)
        } finally {
            a.recycle()
        }
    }

    private fun setupValues(
        metricsGravity: Int,
        metrics: String?,
        value: String?
    ) {
        if (metricsGravity == 0) {
            val params =
                mMetrics!!.layoutParams as LinearLayout.LayoutParams
            params.setMargins(
                (context.resources.displayMetrics.density * 4).toInt(),
                0,
                0,
                0
            )
            mValuesLayout!!.orientation = LinearLayout.HORIZONTAL
        } else {
            mValuesLayout!!.orientation = LinearLayout.VERTICAL
        }
        mValue!!.text = value
        mMetrics!!.text = metrics
    }

    companion object {
        private const val LOG_TAG = "ProgressCircleLayout"
    }
}