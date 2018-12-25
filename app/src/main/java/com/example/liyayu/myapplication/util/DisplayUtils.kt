package com.example.liyayu.myapplication.util

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.util.LruCache
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.ScrollView
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
     * shot the current screen ,with the status but the status is trans *\
     * 获取当前Window 的 DrawingCache 的方式，即decorView的DrawingCache
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

    //    获取NestedScrollView 的 截屏图片
    fun shotNestScrollView(sv: NestedScrollView): Bitmap = shotViewGroup(sv)

    //    获取ScrollView 的 截屏图片
    fun shotScrollView(sv: ScrollView): Bitmap = shotViewGroup(sv)

    private fun shotViewGroup(sv: ViewGroup): Bitmap {
        var h = 0
        for (i in 0 until sv.childCount) {
            val child = sv.getChildAt(i)
            h += child.height
            val color = ContextCompat.getColor(app.applicationContext, R.color.white)
            val background = child.background
            if (background == null) {
                child.setBackgroundColor(color)
            }
            LogUtil.d("di $i ge  ,background = $background")
        }
        val bitmap = Bitmap.createBitmap(sv.width, h, Bitmap.Config.RGB_565)
        sv.draw(Canvas(bitmap))
        return bitmap
    }

    //    获取当前View的DrawingCache
    fun getViewBp(v: View): Bitmap {
        v.isDrawingCacheEnabled = true
        v.buildDrawingCache()
        v.measure(View.MeasureSpec.makeMeasureSpec(v.width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(v.height, View.MeasureSpec.EXACTLY))
        v.layout(v.x.toInt(), v.y.toInt(), v.x.toInt() + v.measuredWidth, v.y.toInt() + v.measuredHeight)
        val b = Bitmap.createBitmap(v.drawingCache, 0, 0, v.measuredWidth, v.measuredHeight)

        v.isDrawingCacheEnabled = false
        v.destroyDrawingCache()
        return b
    }

    /**
     * http://stackoverflow.com/questions/12742343/android-get-screenshot-of-all-lv-items
     *   获取 ListView 的DrawingCache
     *   而ListView就是会回收与重用Item，并且只会绘制在屏幕上显示的ItemView，根据stackoverflow上大神的建议，采用一个List来存储Item的视图，这种方案依然不够好，当Item足够多的时候，可能会发生oom。
     */
    fun shotListView(lv: ListView): Bitmap {
        val adapter = lv.adapter
        val itemscount = adapter.count
        var allitemsheight = 0
        val bmps = ArrayList<Bitmap>()

        for (i in 0 until itemscount) {

            val childView = adapter.getView(i, null, lv)
            childView.measure(
                    View.MeasureSpec.makeMeasureSpec(lv.width, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))

            childView.layout(0, 0, childView.measuredWidth, childView.measuredHeight)
            childView.isDrawingCacheEnabled = true
            childView.buildDrawingCache()
            bmps.add(childView.drawingCache)
            allitemsheight += childView.measuredHeight
        }

        val bigbitmap = Bitmap.createBitmap(lv.measuredWidth, allitemsheight, Bitmap.Config.ARGB_8888)
        val bigcanvas = Canvas(bigbitmap)

        val paint = Paint()
        var iHeight = 0

        for (i in 0 until bmps.size) {
            val bmp = bmps[i]
            bigcanvas.drawBitmap(bmp, 0f, iHeight.toFloat(), paint)
            iHeight += bmp.height
            bmp.recycle()
        }

        return bigbitmap
    }

    /**
     * https://gist.github.com/PrashamTrivedi/809d2541776c8c141d9a
     *  获取 RecyclerView 的DrawingCache
     *  我们都知道，在新的Android版本中，已经可以用RecyclerView来代替使用ListView的场景，相比较ListView，RecyclerView对Item View的缓存支持的更好。可以采用和ListView相同的方案，这里也是在stackoverflow上看到的方案。
     */
    fun shotRecyclerView(view: RecyclerView): Bitmap? {
        val adapter = view.adapter
        var bigBitmap: Bitmap? = null
        if (adapter != null) {
            val size = adapter.itemCount
            var height = 0
            val paint = Paint()
            var iHeight = 0
            val maxMemory = (Runtime.getRuntime().maxMemory() / 1024)

            // Use 1/8th of the available memory for this memory cache.
            val cacheSize = maxMemory / 8
            val bitmaCache = LruCache<String, Bitmap>(cacheSize.toInt())
            for (i in 0 until size) {
                val holder = adapter.createViewHolder(view, adapter.getItemViewType(i))
                adapter.onBindViewHolder(holder, i)
                holder.itemView.measure(View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
                holder.itemView.layout(0, 0, holder.itemView.measuredWidth,
                        holder.itemView.measuredHeight)
                holder.itemView.isDrawingCacheEnabled = true
                holder.itemView.buildDrawingCache()
                val drawingCache = holder.itemView.drawingCache
                if (drawingCache != null) {
                    bitmaCache.put(i.toString(), drawingCache)
                }
                height += holder.itemView.measuredHeight
            }

            bigBitmap = Bitmap.createBitmap(view.measuredWidth, height, Bitmap.Config.ARGB_8888)
            val bigCanvas = Canvas(bigBitmap)
            val lBackground = view.background
            if (lBackground is ColorDrawable) {
                val lColor = lBackground.color
                bigCanvas.drawColor(lColor)
            }

            for (i in 0 until size) {
                val bitmap = bitmaCache.get(i.toString())
                bigCanvas.drawBitmap(bitmap, 0f, iHeight.toFloat(), paint)
                iHeight += bitmap.height
                bitmap.recycle()
            }
        }
        return bigBitmap
    }

}