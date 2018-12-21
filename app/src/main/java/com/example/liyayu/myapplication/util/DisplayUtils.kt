package com.example.liyayu.myapplication.util

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v4.widget.NestedScrollView
import android.util.DisplayMetrics
import com.example.liyayu.myapplication.R

/**
 * Created by liyayu on 2018/8/20.
 * 获取屏幕参数，px、dp、sp转换
 * 屏幕截取相关方法
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

    /**
     * shot the current screen ,with the status but the status is trans *
     * @param ctx current activity
     */
    fun shotActivity(ctx: Activity): Bitmap {
        val view = ctx.window.decorView
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()

        val bp = Bitmap.createBitmap(view.drawingCache, 0, 0, view.measuredWidth,
                view.measuredHeight)

        view.isDrawingCacheEnabled = false
        view.destroyDrawingCache()
        return bp
    }

    fun shotNestScrollView(sv: NestedScrollView): Bitmap {
        var h = 0
        for (i in 0 until sv.childCount) {
            val child = sv.getChildAt(i)
            h += child.height
            val color = ContextCompat.getColor(app.applicationContext,R.color.white)
            val background = child.background
            if (background == null){
                child.setBackgroundColor(color)
            }
            LogUtil.d("di $i ge  ,background = $background")
        }
        val bitmap = Bitmap.createBitmap(sv.width, h, Bitmap.Config.RGB_565)
        sv.draw(Canvas(bitmap))
        return bitmap
    }
}