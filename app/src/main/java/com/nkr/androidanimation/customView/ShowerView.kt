package com.nkr.androidanimation.customView

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import androidx.core.graphics.withTranslation
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.nkr.androidanimation.R
import kotlin.math.min

class ShowerView@JvmOverloads
constructor(context:Context,
            attrs: AttributeSet? = null,
            defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    private lateinit var shader:BitmapShader
    private lateinit var rect: Rect
    private lateinit var roundedRect: RectF
    private val edge = 12.0f
    private val startPoint = 5
    //animation
    private val ANIMATION_DURATION = 4000L
    private val ANIMATION_DELAY: Long = 1000
    private val COLOR_ADJUSTER = 5
    private val maxRadius = 50f
    private var minRadius = 0.0f




    private var mRadius = 0.0f // Radius of the circle.
    private var circleStartXPosition = 0.0f
    private var circleStartYPosition = 0.0f




    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        // Paint styles used for rendering are initialized here. This
        // is a performance optimization, since onDraw() is called
        // for every screen refresh.
        style = Paint.Style.FILL
        color =  Color.CYAN
        textAlign = Paint.Align.CENTER
        textSize = 20.0f
        typeface = Typeface.create("", Typeface.NORMAL)


    }


    private val paintDot = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        // Paint styles used for rendering are initialized here. This
        // is a performance optimization, since onDraw() is called
        // for every screen refresh.
        style = Paint.Style.FILL
      //  color =  Color.WHITE
        textAlign = Paint.Align.CENTER
        textSize = 20.0f
        typeface = Typeface.create("", Typeface.NORMAL)


    }



    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        roundedRect = RectF(startPoint.toFloat(),startPoint.toFloat(),width.toFloat(),height.toFloat())
       // rect = Rect(startPoint,startPoint,width,height)
        paint.shader = LinearGradient(100f, 100f, 270f, h.toFloat(), ContextCompat.getColor(context,R.color.colorMustardLight), ContextCompat.getColor(context,R.color.colorMustardDark), Shader.TileMode.MIRROR)

        mRadius = (min(width, height) / 12 *.5).toFloat()

        minRadius = mRadius

        //grow animation
        val growAnimator = ObjectAnimator.ofFloat(
            this,
            "radius", mRadius, maxRadius
        )
        growAnimator.duration = ANIMATION_DURATION
        growAnimator.interpolator = LinearInterpolator()
        // shrink animation
        val shrinkAnimator = ObjectAnimator.ofFloat(
            this,
            "radius", maxRadius, minRadius
        )
            shrinkAnimator.duration = ANIMATION_DURATION
        shrinkAnimator.interpolator = LinearOutSlowInInterpolator()
        shrinkAnimator.startDelay = ANIMATION_DELAY

        // mover animation

        val moverAnimator = ObjectAnimator.ofFloat(
            this,
            "radius", circleStartXPosition
        )

        moverAnimator.duration = ANIMATION_DURATION
        moverAnimator.interpolator = LinearOutSlowInInterpolator()



       val animatorSet = AnimatorSet()
        animatorSet.play(growAnimator).before(shrinkAnimator)
        animatorSet.play(shrinkAnimator).after(growAnimator)
        animatorSet.playTogether(moverAnimator,growAnimator,shrinkAnimator)


        // If there is an animation running, cancel it.
// This resets the AnimatorSet and its animations to the starting values.
        if (animatorSet != null && animatorSet.isRunning) {
            animatorSet.cancel()
        }
        // Start the animation sequence.
        // Start the animation sequence.
        animatorSet.start()

    }

    fun setRadius(radius:Float){
        mRadius = radius
        paintDot.color = Color.CYAN + radius.toInt()*COLOR_ADJUSTER
        paintDot.alpha = 90
      //  TRANSLATION_Y
        circleStartXPosition +=.2f
        // Updating the property does not automatically redraw.
        invalidate()

    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRoundRect(roundedRect,edge,edge,paint)
      //  canvas?.drawRect(rect,paint)

       canvas.withTranslation(100f,100f) {

           drawCircle(circleStartXPosition, circleStartYPosition, mRadius, paintDot)
       }

    }

}