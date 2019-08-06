package com.example.liyayu.myapplication.demoViews.loading_demo

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.liyayu.myapplication.R
import com.example.liyayu.myapplication.demoViews.loading_demo.Gloading.*
import kotlinx.android.synthetic.main.view_global_loading_status.view.*

/**
 * <pre>
 *     author : JX
 *     e-mail : liyayu@ffde.com
 *     time   : 2019/08/02
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class GlobalLoadingStatusView : LinearLayout, View.OnClickListener {

    //    (context: Context, retryTask: Runnable)
    constructor(context: Context, retryTask: Runnable) : this(context, null, retryTask)

    constructor(context: Context, attrs: AttributeSet?, retryTask: Runnable) : this(context, attrs, 0, retryTask)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, retryTask: Runnable) : super(context, attrs, defStyleAttr) {
        if (isInEditMode) {
            return
        }
        initView(retryTask)
    }

    private val mTextView by lazy { text }
    private var mRetryTask: Runnable? = null
    private val mImageView by lazy { image }
    fun initView(retryTask: Runnable) {
        orientation = VERTICAL
        gravity = Gravity.CENTER_HORIZONTAL
        LayoutInflater.from(context).inflate(R.layout.view_global_loading_status, this, true)

        this.mRetryTask = retryTask
        setBackgroundColor(-0xf0f10)
    }

    fun setMsgViewVisibility(visible: Boolean) {
        mTextView.visibility = if (visible) View.VISIBLE else View.GONE
    }

    fun setStatus(status: Int) {
        var show = true
        var onClickListener: OnClickListener? = null
        var image = R.mipmap.icon_loading
        var str = R.string.str_none
        when (status) {
            STATUS_LOAD_SUCCESS -> show = false
            STATUS_LOADING -> str = R.string.loading
            STATUS_LOAD_FAILED -> {
                str = R.string.load_failed
                image = R.mipmap.icon_failed
                val networkConn = isNetworkConnected(context)
                if (networkConn != null && !networkConn) {
                    str = R.string.load_failed_no_network
                    image = R.mipmap.icon_no_wifi
                }
                onClickListener = this
            }
            STATUS_EMPTY_DATA -> {
                str = R.string.empty
                image = R.mipmap.icon_empty
            }
            else -> {
            }
        }
        mImageView.setImageResource(image)
        if (status == STATUS_LOADING) {
            startRotateAnimation(mImageView)
        } else {
            mImageView.clearAnimation()
        }
        setOnClickListener(onClickListener)
        mTextView.setText(str)
        visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onClick(v: View) {
        mRetryTask?.run()
    }
}