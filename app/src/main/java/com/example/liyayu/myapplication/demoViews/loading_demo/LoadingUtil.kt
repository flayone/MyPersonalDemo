package com.example.liyayu.myapplication.demoViews.loading_demo

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.liyayu.myapplication.baseFramework.MyViewHolder
import kotlinx.android.synthetic.main.activity_loading_activity.*
import java.util.*
import java.util.logging.Handler

/**
 * <pre>
 *     author : JX
 *     e-mail : liyayu@ffde.com
 *     time   : 2019/08/02
 *     desc   :
 *     version: 1.0
 * </pre>
 */
val HIDE_LOADING_STATUS_MSG = "hide_loading_status_msg"

fun getErrorImage(): String {
    return "http://www." + System.currentTimeMillis() + ".com/abc.png"
}

fun getRandomImage(): String {
    val id = (Math.random() * 100000).toInt()
    return String.format(Locale.CHINA, "https://www.thiswaifudoesnotexist.net/example-%d.jpg", id)
}


/**
 * check if the network connected or not
 * @param context context
 * @return true: connected, false:not, null:unknown
 */
fun isNetworkConnected(context: Context): Boolean? {
    try {
        val cm = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm != null) {
            val networkInfo = cm.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    } catch (ignored: Exception) {
    }

    return null
}

abstract class LoadingRvItemAdapter<T>(private var list: MutableList<T>) :
        RecyclerView.Adapter<LoadingViewHolder>() {
    open lateinit var mContext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadingViewHolder {
        mContext = parent.context
        return onCreateViewHolder(parent)
    }

    abstract fun onCreateViewHolder(parent: ViewGroup): LoadingViewHolder

    fun notifyData(data: MutableList<T>) {
        list = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    // 如果想要每次都调用onBindViewHolder()刷新item数据，就要重写getItemViewType()，让其返回position，否则很容易产生数据错乱的现象。
    override fun getItemViewType(position: Int): Int = position

    override fun onBindViewHolder(holder: LoadingViewHolder, position: Int) =
            onBind(holder, position, list[position])

    abstract fun onBind(holderView: LoadingViewHolder, position: Int, data: T)
}

class LoadingViewHolder(private val context: Context, private val holder: Gloading.Holder, private val imageView: ImageView, itemView: View) : RecyclerView.ViewHolder(itemView), Runnable {
    private var curUrl: String = ""

    override fun run() {
        showImage(curUrl)
    }

    init {
        holder.withRetry(this)
    }

    fun showImage(url: String) {
        curUrl = url
        holder.showLoading()
        Glide.with(context).load(url).listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {

                holder.showLoadFailed()
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                holder.showLoadSuccess()
                return false
            }

        }).into(imageView)
    }
}


//旋转动画
fun startRotateAnimation(view: View) {
    val rotateAnimation = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f)
    rotateAnimation.duration = 1000
    rotateAnimation.repeatCount = -1
    val lin = LinearInterpolator()
    rotateAnimation.interpolator = lin
    view.startAnimation(rotateAnimation)
}
