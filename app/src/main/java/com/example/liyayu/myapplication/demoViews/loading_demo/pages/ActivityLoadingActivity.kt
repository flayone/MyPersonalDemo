package com.example.liyayu.myapplication.demoViews.loading_demo.pages

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.liyayu.myapplication.R
import com.example.liyayu.myapplication.demoViews.loading_demo.BaseLoadingActivity
import com.example.liyayu.myapplication.demoViews.loading_demo.Gloading
import com.example.liyayu.myapplication.demoViews.loading_demo.getErrorImage
import com.example.liyayu.myapplication.demoViews.loading_demo.getRandomImage
import kotlinx.android.synthetic.main.activity_loading_activity.*

/**
 * <pre>
 *     author : JX
 *     e-mail : liyayu@ffde.com
 *     time   : 2019/08/02
 *     desc   :
 *     version: 1.0
 * </pre>
 */
open class ActivityLoadingActivity : BaseLoadingActivity() {

    var loadingTime: Long = 1000
    protected var mViewHolder: Gloading.Holder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_activity)

        initViewLoader()
        startViewLoadData()
    }

    /////////////////////普通的activity的loading  --开始////////////////
    private fun startViewLoadData() {
        showLoading()
        Handler().postDelayed({ showLoadFailed() }, loadingTime)
    }

    private fun startReLoadData() {
        showLoading()
        Handler().postDelayed({
            showLoadSuccess()
            startLoadData(getErrorImage())
        }, loadingTime)
    }

    override fun onLoadRetry() {
        //change picture url to a correct one
//        loadData(getRandomImage())
        startReLoadData()
    }
    /////////////////////普通的activity的loading  --结束////////////////


    /////////////////////自定义单个View的loading  --开始////////////////

    private fun initViewLoader() {
        if (mViewHolder == null) {
            mViewHolder = Gloading.getDefault().wrap(iv_ala_show).withRetry { onViewLoadRetry() }
        }
    }

    private fun startLoadData(picUrl: String) {
        d(picUrl)
        initViewLoader()
        mViewHolder?.showLoading()
        Glide.with(this).load(picUrl).listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                initViewLoader()
                mViewHolder?.showLoadFailed()
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                initViewLoader()
                mViewHolder?.showLoadSuccess()
                return false
            }

        }).into(iv_ala_show)
    }

    private fun onViewLoadRetry() {
        //change picture url to a correct one
//        loadData(getRandomImage())
        startLoadData(getRandomImage())
    }
    /////////////////////自定义单个View的loading  --结束////////////////

}