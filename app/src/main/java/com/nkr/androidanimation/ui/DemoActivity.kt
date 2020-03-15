/*
 * Copyright (C) 2018 The Android Open Source Project
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

package com.nkr.androidanimation.ui

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import com.nkr.androidanimation.R
import com.nkr.androidanimation.customView.CircleAnimationDrawable
import com.nkr.androidanimation.customView.PathAnimationDrawable
import com.nkr.androidanimation.customView.PolygonLapsDrawable
import com.nkr.androidanimation.customView.StraightPathAnimation
import kotlinx.android.synthetic.main.activity_path_animation.*
import kotlinx.android.synthetic.main.layout_polygonal_drawable_animation.*

@RequiresApi(Build.VERSION_CODES.LOLLIPOP) // for View#clipToOutline
class DemoActivity : AppCompatActivity() {

    private lateinit var container: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = intent.getIntExtra("layout_file_id", R.layout.motion_01_basic)
        setContentView(layout)
        container = findViewById(R.id.motionLayout)
/*
        if (layout == R.layout.motion_11_coordinatorlayout) {
            val icon = findViewById<ImageView>(R.id.icon)
            icon?.clipToOutline = true
        }*/

        if(layout == R.layout.polygonal_animation){
            iv_polygon.setBackgroundDrawable(PolygonLapsDrawable())

        }

        if(layout == R.layout.activity_path_animation){
            iv_path.setBackgroundDrawable(PathAnimationDrawable())

        }

        if(layout == R.layout.layout_circle_animation_drawable){
            iv_path.setBackgroundDrawable(CircleAnimationDrawable())

        }


        if(layout == R.layout.layout_starfield){
            iv_path.setBackgroundDrawable(PathAnimationDrawable())

        }

        if(layout == R.layout.layout_path_animation){
            iv_path.setBackgroundDrawable(StraightPathAnimation())

        }





        val debugMode = if (intent.getBooleanExtra("showPaths", false)) {
            MotionLayout.DEBUG_SHOW_PATH
        } else {
            MotionLayout.DEBUG_SHOW_NONE
        }
        (container as? MotionLayout)?.setDebugMode(debugMode)
    }

    fun changeState(v: View?) {
        val motionLayout = container as? MotionLayout ?: return
        if (motionLayout.progress > 0.5f) {
            motionLayout.transitionToStart()
        } else {
            motionLayout.transitionToEnd()
        }
    }
}