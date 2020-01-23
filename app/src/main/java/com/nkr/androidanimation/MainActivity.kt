package com.nkr.androidanimation

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rotateButton.setOnClickListener {
            val animator = ObjectAnimator.ofFloat(star, View.ROTATION, -360f, 0f)
          //  animator.duration = 1000
           // animator.disableViewDuringAnimation(rotateButton)
            animator.start()
        }
    }
}
