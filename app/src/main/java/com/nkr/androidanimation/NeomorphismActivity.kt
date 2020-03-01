package com.nkr.androidanimation

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.LinearInterpolator
import com.nkr.androidanimation.customView.PolygonLapsDrawable
import kotlinx.android.synthetic.main.activity_neomorphism.*
import kotlinx.android.synthetic.main.layout_polygonal_drawable_animation.*

class NeomorphismActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_polygonal_drawable_animation)


        iv_polygon.setBackgroundDrawable(PolygonLapsDrawable())


        setTheMotion()


    }

    private fun setTheMotion() {

    }
}
