package com.example.liyayu.myapplication.customWidgets

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import com.example.liyayu.myapplication.util.BaseClickListener
import com.example.liyayu.myapplication.util.dp2px
import com.example.liyayu.myapplication.util.showToast

/**
 * Created by liyayu on 2018/11/16.
 * 可拖拽布局
 */
class DragView : Button {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private var mScreenWidth: Int = 0
    private var mScreenHeight: Int = 0//屏幕宽高
    private var mOriginalX: Float = 0.toFloat()
    private var mOriginalY: Float = 0.toFloat()//手指按下时的初始位置
    private var mDistanceX: Float = 0.toFloat()
    private var mDistanceY: Float = 0.toFloat()//记录手指与view的左上角的距离
    private var hasAutoPullToBorder: Boolean = true//标记是否开启自动拉到边缘功能
    private var mListener: BaseClickListener? = null

    var mLeft = 0
    var mRight = 0
    var mTop = 0
    var mBottom = 0

    private fun init() {

    }

    fun isAutu(b:Boolean){
        hasAutoPullToBorder = b
    }
    fun setOnClickListener(listener: BaseClickListener) {
        mListener = listener
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        val statusHeight = resources.getDimensionPixelSize(resourceId)
        mScreenWidth = resources.displayMetrics.widthPixels
        //需要考虑状态栏的高度
        mScreenHeight = resources.displayMetrics.heightPixels- statusHeight
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mOriginalX = event.x
                mOriginalY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                val xDistance = (event.x - mOriginalX).toInt()
                val yDistance = (event.y - mOriginalY).toInt()
                mLeft = left + xDistance
                mRight = right + xDistance
                mTop = top + yDistance
                mBottom = bottom + yDistance
                //当水平或者垂直滑动距离大于10,才算拖动事件
                if (Math.abs(event.x) > 10 || Math.abs(event.y) > 10) {
                    if (mLeft < 0) {
                        mLeft = 0
                        mRight = measuredWidth
                    }
                    if (mRight > mScreenWidth) {
                        mRight = mScreenWidth
                        mLeft = mScreenWidth - measuredWidth
                    }
                    if (mTop < 0) {
                        mTop = 0
                        mBottom = measuredHeight
                    }
                    if (mBottom > mScreenHeight - measuredHeight / 2) {
                        mBottom = mScreenHeight
                        mTop = mScreenHeight - measuredHeight
                    }
                    layout(mLeft, mTop, mRight, mBottom)
                }
            }
            MotionEvent.ACTION_UP -> {
//在拖动过按钮后，如果其他view刷新导致重绘，会让按钮重回原点，所以需要更改布局参数
                startAutoPull(layoutParams as ViewGroup.MarginLayoutParams)
                //如果移动距离过小，则判定为点击
                if (Math.abs(event.x) < dp2px(5f) && Math.abs(event.y) < dp2px(5f)) {
                    mListener?.onClick()
                }
                //消除警告
                performClick()
            }
        }
        return true
    }

    /**
     * 开启自动拖拽
     * @param lp 控件布局参数
     */
    private fun startAutoPull(lp: ViewGroup.MarginLayoutParams) {
        if (!hasAutoPullToBorder) {
            layout(mLeft, mTop, mRight, mBottom)
            lp.setMargins(mLeft, mTop, 0, 0)
            layoutParams = lp
//            if (mListener != null) {
//                mListener.onDragged(v, left, top)
//            }
            return
        }
        //当用户拖拽完后，让控件根据远近距离回到最近的边缘
        var end = 0f
        if (left + measuredWidth / 2 <= mScreenWidth / 2) {
            end = (mScreenWidth - measuredWidth).toFloat()
            mLeft = 0
            mRight = measuredWidth
        } else {
            mLeft = mScreenWidth - measuredWidth
            mRight = mScreenWidth
            end = 0f
        }
        showToast("end = $end")
        val animator = ValueAnimator.ofFloat(left.toFloat(), mLeft.toFloat())
        animator.interpolator = DecelerateInterpolator()
        animator.addUpdateListener { animation ->
            val leftMargin = (animation.animatedValue as Float).toInt()
            layout(mLeft, mTop, mRight, mBottom)
            lp.setMargins(leftMargin, mTop, 0, 0)
            layoutParams = lp
        }
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
//                if (mListener != null) {
//                    mListener.onDragged(v, finalEnd.toInt(), top)
//                }
            }
        })
        animator.duration = 400
        animator.start()
    }
}