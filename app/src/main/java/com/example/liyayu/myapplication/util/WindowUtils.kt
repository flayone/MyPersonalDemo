package com.example.liyayu.myapplication.util

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.view.Surface


/**
 * Created by liyayu on 2019/1/22.
 * 窗口相关方法集合
 */

/**
 * 获取当前窗口的旋转角度
 *
 * @param activity activity
 * @return  int
 */
fun getDisplayRotation(activity: Activity): Int {
    return when (activity.windowManager.defaultDisplay.rotation) {
        Surface.ROTATION_0 -> 0
        Surface.ROTATION_90 -> 90
        Surface.ROTATION_180 -> 180
        Surface.ROTATION_270 -> 270
        else -> 0
    }
}

/**
 * 当前是否是横屏
 *
 * @param context  context
 * @return  boolean
 */
fun isLandscape(context: Context): Boolean {
    return context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
}

/**
 * 当前是否是竖屏
 *
 * @param context  context
 * @return   boolean
 */
fun isPortrait(context: Context): Boolean {
    return context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
}

/**
 * 调整窗口的透明度  1.0f,0.5f 变暗
 * @param from  from>=0&&from<=1.0f
 * @param to  to>=0&&to<=1.0f
 * @param context  当前的activity
 */
fun dimBackground(from: Float, to: Float, context: Activity) {
    val window = context.window
    val valueAnimator = ValueAnimator.ofFloat(from, to)
    valueAnimator.duration = 500
    valueAnimator.addUpdateListener { animation ->
        val params = window.attributes
        params.alpha = animation.animatedValue as Float
        window.attributes = params
    }
    valueAnimator.start()
}