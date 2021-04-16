package com.nkr.androidanimation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import com.nkr.androidanimation.R


class DynamicLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic_layout)

        val cly = findViewById<LinearLayout>(R.id.cly)

        for (i in 0..10) {
          /*  val layoutInflater =
                this.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            cly.addView(layoutInflater.inflate(R.layout.item_dynamic_layout, cly, false))*/

            val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val child = inflater.inflate(R.layout.item_dynamic_layout, null)
            val title = child.findViewById<TextView>(R.id.tv_demo_one)
            cly.addView(child)
        }


    }
}