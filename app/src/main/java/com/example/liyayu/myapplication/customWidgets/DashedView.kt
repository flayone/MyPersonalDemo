package com.chexiang.myhome.widget.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.example.liyayu.myapplication.R

/**
 * Created by liyayu on 2018/11/20.
 * 垂直的虚线View
 */
class DashedView : View {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, android.R.attr.editTextStyle)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

    }

    var mHeight = 0f
    var mWidth = 0f

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = measuredWidth.toFloat()
        mHeight = measuredHeight.toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val paint = Paint()
        val path = Path()
        val mPathEffect = DashPathEffect(floatArrayOf(5f, 5f, 5f, 5f), 1f)

        paint.run {
            reset()
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = 1.5f
            color = resources.getColor(R.color.red_button)
            pathEffect = mPathEffect
        }

        path.moveTo(mWidth / 2, 0f)
        path.lineTo(mWidth / 2, mHeight)
        canvas.drawPath(path, paint)
    }
}