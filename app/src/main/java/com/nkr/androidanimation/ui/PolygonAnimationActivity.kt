package com.nkr.androidanimation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nkr.androidanimation.R
import com.nkr.androidanimation.customView.PolygonLapsDrawable
import kotlinx.android.synthetic.main.layout_polygonal_drawable_animation.*

class PolygonAnimationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_polygonal_drawable_animation)


        iv_polygon.setBackgroundDrawable(PolygonLapsDrawable())


        setTheMotion()


    }

    private fun setTheMotion() {

    }
}
