package com.nkr.androidanimation.customView

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
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


    private var radius = 0.0f                  // Radius of the circle.



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
        color =  Color.WHITE
        textAlign = Paint.Align.CENTER
        textSize = 20.0f
        typeface = Typeface.create("", Typeface.NORMAL)


    }



    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        roundedRect = RectF(startPoint.toFloat(),startPoint.toFloat(),width.toFloat(),height.toFloat())
       // rect = Rect(startPoint,startPoint,width,height)
        paint.shader = LinearGradient(100f, 100f, 270f, h.toFloat(), ContextCompat.getColor(context,R.color.colorMustardLight), ContextCompat.getColor(context,R.color.colorMustardDark), Shader.TileMode.MIRROR)

        radius = (min(width, height) / 2.0 * 0.5).toFloat()


    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRoundRect(roundedRect,edge,edge,paint)
      //  canvas?.drawRect(rect,paint)


        canvas.drawCircle(100f,100f,radius/12,paintDot)

    }

}