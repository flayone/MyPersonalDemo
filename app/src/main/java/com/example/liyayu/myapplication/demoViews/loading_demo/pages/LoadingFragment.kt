package com.example.liyayu.myapplication.demoViews.loading_demo.pages

import android.graphics.drawable.Drawable
import android.os.Handler
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.liyayu.myapplication.R
import com.example.liyayu.myapplication.demoViews.loading_demo.Gloading
import com.example.liyayu.myapplication.demoViews.loading_demo.getErrorImage
import com.example.liyayu.myapplication.demoViews.loading_demo.getRandomImage
import com.example.liyayu.myapplication.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_loading.*

/**
 * <pre>
 *     author : JX
 *     e-mail : liyayu@ffde.com
 *     time   : 2019/08/05
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class LoadingFragment : BaseFragment(R.layout.fragment_loading) {
    var holder: Gloading.Holder? = null
    protected var mViewHolder: Gloading.Holder? = null

    var loadingTime: Long = 1000

    override fun initView() {

        initLoader()
        initViewLoader()
        startLoadData()
    }

    /////////////////////普通的activity的loading  --开始////////////////

    private fun initLoader() {
        if (holder == null) {
            holder = Gloading.getDefault().wrap(this.activity).withRetry {
                onLoadRetry()
            }
        }
    }

    private fun startLoadData() {
        holder?.showLoading()
        Handler().postDelayed({ holder?.showLoadFailed() }, loadingTime)
    }

    private fun startReLoadData() {
        holder?.showLoading()
        Handler().postDelayed({
            holder?.showLoadSuccess()
            startLoadData(getErrorImage())
        }, loadingTime)
    }

    private fun onLoadRetry() {
        //change picture url to a correct one
//        loadData(getRandomImage())
        startReLoadData()
    }
    /////////////////////普通的activity的loading  --结束////////////////


    /////////////////////自定义单个View的loading  --开始////////////////

    private fun initViewLoader() {
        if (mViewHolder == null) {
            mViewHolder = Gloading.getDefault().wrap(iv_fl_show).withRetry { onViewLoadRetry() }
        }
    }

    private fun startLoadData(picUrl: String) {
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

        }).into(iv_fl_show)
    }

    private fun onViewLoadRetry() {
        //change picture url to a correct one
//        loadData(getRandomImage())
        startLoadData(getRandomImage())
    }
    /////////////////////自定义单个View的loading  --结束////////////////
}