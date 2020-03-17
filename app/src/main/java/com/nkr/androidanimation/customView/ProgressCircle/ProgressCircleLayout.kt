package com.nkr.androidanimation.customView.ProgressCircle

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.Interpolator
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.nkr.androidanimation.R
import com.nkr.androidanimation.customView.ProgressCircle.ExampleJava.OnCircleAnimationListener

class ProgressCircleLayout : FrameLayout {

    private var mCircle: ProgressCircularView? = null

    private var mValuesLayout: LinearLayout? = null
    private var mValue: TextView? = null
    private var mMetrics: TextView? = null

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {

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


    fun setInterpolator(i: Interpolator?) {
        mCircle?.setInterpolator(i!!)
    }


    fun showAnimation() {
        mCircle!!.showAnimation()
    }

    private fun findViews() {
        View.inflate(context, R.layout.layout_progress_circle_view, this)
        mCircle = findViewById<View>(R.id.circle) as ProgressCircularView

        mValuesLayout = findViewById<View>(R.id.values_layout) as LinearLayout
        mValue = findViewById<View>(R.id.value) as TextView
        mMetrics = findViewById<View>(R.id.metrics) as TextView
    }


    fun setupValues(
        c: Context,
        attrs: AttributeSet?
    ) {
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
                mMetrics?.getLayoutParams() as LinearLayout.LayoutParams
            params.setMargins(
                (context.resources.displayMetrics.density * 4).toInt(),
                0,
                0,
                0
            )
            mValuesLayout?.setOrientation(LinearLayout.HORIZONTAL)
        } else {
            mValuesLayout?.setOrientation(LinearLayout.VERTICAL)
        }
        mValue?.setText(value)
        mMetrics?.setText(metrics)
    }


}