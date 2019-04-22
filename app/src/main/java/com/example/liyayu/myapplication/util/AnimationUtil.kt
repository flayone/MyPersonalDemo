package com.example.liyayu.myapplication.util

import android.animation.Animator
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.View
import android.view.ViewAnimationUtils

/**
 * Created by liyayu on 2019/4/19.
 * 动画效果
 */

/**
 * 幕布动画效果
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun startActDrop(view: View, centerX: Int, centerY: Int, startRadius: Float, endRadius: Float,endAnim: (() -> Unit),duration: Long = 300) {
    val animator = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius)
    animator.duration = duration
    animator.start()
    animator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(p0: Animator?) {
        }

        override fun onAnimationStart(p0: Animator?) {

        }

        override fun onAnimationCancel(p0: Animator?) {
        }

        override fun onAnimationEnd(p0: Animator?) {
            endAnim.invoke()
        }
    })
}