package com.nkr.androidanimation.customView.ProgressCircle

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.nkr.androidanimation.R

class ProgressCircleLayout : FrameLayout {

    private var mCircle: ProgressCircularView? = null

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {

        init(context, attrs)
        mCircle!!.init(context, attrs!!)

    }

    private fun init(c: Context, attrs: AttributeSet?) {
        findViews()

     //   setOnCircleAnimationListener()
        // setupValues(c, attrs)
    }

    private fun findViews() {
        View.inflate(context, R.layout.layout_progress_circle_view, this)
        mCircle = findViewById<View>(R.id.circle) as ProgressCircularView

      /*  mValuesLayout = findViewById<View>(R.id.values_layout) as LinearLayout
        mValue = findViewById<View>(R.id.value) as TextView
        mMetrics = findViewById<View>(R.id.metrics) as TextView*/
    }


}