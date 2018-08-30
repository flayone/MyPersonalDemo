package com.example.liyayu.myapplication.util

import android.app.Activity
import android.os.Build
import android.util.DisplayMetrics

/**
 * Created by liyayu on 2018/8/20.
 * 获取屏幕参数，px、dp、sp转换
 */
class DisplayUtils {
    private var isInitialize = false
    var screenWidth: Int = 0
    var screenHeight: Int = 0
    var screenDpi: Int = 0
    var density = 1f
    var scaledDensity: Float = 0.toFloat()
    //保证并发性，把JVM方法标记为同步
    @Synchronized
    fun initScreen(activity: Activity) {
        //只执行一次
        if (isInitialize) return
        isInitialize = true
        val display = activity.windowManager.defaultDisplay
        val metric = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealMetrics(metric)
        } else {
            display.getMetrics(metric)
        }

        screenWidth = metric.widthPixels
        screenHeight = metric.heightPixels
        screenDpi = metric.densityDpi
        density = metric.density
        scaledDensity = metric.scaledDensity
    }

    fun px2dip(inParam: Float): Int {
        return (inParam / density + 0.5f).toInt()
    }

    fun dip2px(inParam: Float): Int {
        return (inParam * density + 0.5f).toInt()
    }

    fun px2sp(inParam: Float): Int {
        return (inParam / scaledDensity + 0.5f).toInt()
    }

    fun sp2px(inParam: Float): Int {
        return (inParam * scaledDensity + 0.5f).toInt()
    }
}