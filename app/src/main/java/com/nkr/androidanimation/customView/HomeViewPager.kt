package com.nkr.androidanimation.customView

import android.content.Context
import android.os.CountDownTimer
import androidx.viewpager.widget.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import com.nkr.androidanimation.base.App
import com.nkr.androidanimation.utils.GestureActionType
import java.util.*


class HomeViewPager : ViewPager {

    var startX: Float = 0F
    var startY: Float = 0F

    //normal click interval
    private val clickCountDownInterval: Long = 100
    val tapDistance = 100

    //long click interval
    var startClickTime: Long? = null
    private var countDownTimer: CountDownTimer? = null
    val defaultCountDownSecond: Long = 500
    val longClickCountDownInterval: Long = 1000
    var isLongTap = false

    //swipe
    var largestDistance = 0f
    private val swipeDistance = 250

    constructor(context: Context) : super(context) {
        //        setMyScroller();
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        //        setMyScroller();
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return true
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        val pointerCount = ev.pointerCount
        for (i in 0 until pointerCount) {
            val x = ev.x
            val y = ev.y
            val action = ev.actionMasked
            if (action == MotionEvent.ACTION_DOWN) {
                largestDistance = 0f

                //case of just pressed down
                startClickTime = Calendar.getInstance().timeInMillis

                //case of just pressed down
                startX = x
                startY = y

                //start Timer
                startTimer()
            } else if (action == MotionEvent.ACTION_MOVE) {
                //case of swipe
                val distance = getDistance(ev)
                largestDistance = if (distance > largestDistance) distance else largestDistance
            } else if (action == MotionEvent.ACTION_UP) {
                val clickDuration = Calendar.getInstance().timeInMillis - startClickTime!!
                if (countDownTimer != null) {
                    countDownTimer?.cancel()
                    countDownTimer = null
                }
                if (!isLongTap) {
                    val distance = getDistance(ev)
                    val xDifferent = Math.abs(startX - x)
                    val yDifferent = Math.abs(startY - y)
                    //case of just release up
                    if (clickDuration < clickCountDownInterval && distance < tapDistance && !App.postChanging) {
                        //case of normal click
                        pagerListener?.action(startX, startY, GestureActionType.CLICK)
                    } else {
                        //case of swipe
                        if (xDifferent < yDifferent) {
                            //case of move up down
                            if (y < startY && yDifferent > swipeDistance && !App.postChanging) {
                                //case of move up
                                pagerListener?.action(startX, startY, GestureActionType.SWIPE_UP)
                            } else {
                                //case of move down
                            }
                        }
                    }
                }
                isLongTap = false
            }
        }
        return super.onTouchEvent(ev)
    }

    /**
     * start count down timer
     */
    private fun startTimer() {
        countDownTimer = object : CountDownTimer(defaultCountDownSecond, longClickCountDownInterval) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                if (largestDistance < tapDistance && !App.postChanging) {
                    isLongTap = true
                    pagerListener?.action(startX, startY, GestureActionType.LONG_CLICK)
                }
                countDownTimer = null
            }
        }.start()
    }

    /**
     * get distance of swipe distance
     */
    private fun getDistance(ev: MotionEvent): Float {
        val xDifferent = Math.abs(startX - ev.x)
        val yDifferent = Math.abs(startY - ev.y)

        return if (xDifferent > yDifferent) {
            xDifferent
        } else {
            yDifferent
        }
    }


    /**
     * listener related
     */
    var pagerListener: PagerListener? = null

    interface PagerListener {
        fun action(x: Float, y: Float, gestureActionType: GestureActionType)
    }


}