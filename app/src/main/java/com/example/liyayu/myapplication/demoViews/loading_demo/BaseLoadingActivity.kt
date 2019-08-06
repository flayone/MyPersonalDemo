package com.example.liyayu.myapplication.demoViews.loading_demo

import android.os.Bundle
import com.example.liyayu.myapplication.R
import com.example.liyayu.myapplication.baseFramework.BaseKotlinActivity

/**
 * <pre>
 *     author : JX
 *     e-mail : liyayu@ffde.com
 *     time   : 2019/08/02
 *     desc   :
 *     version: 1.0
 * </pre>
 */
abstract class BaseLoadingActivity :BaseKotlinActivity(){
    protected var mHolder: Gloading.Holder? = null

    protected open fun initLoadingStatusViewIfNeed() {
        if (mHolder == null) {
            //bind status view to activity root view by default
            mHolder = Gloading.getDefault().wrap(this).withRetry { onLoadRetry() }
        }
    }

    protected open fun onLoadRetry() {
        // override this method in subclass to do retry task
    }

    fun showLoading() {
        initLoadingStatusViewIfNeed()
        mHolder?.showLoading()
    }

    fun showLoadSuccess() {
        initLoadingStatusViewIfNeed()
        mHolder?.showLoadSuccess()
    }

    fun showLoadFailed() {
        initLoadingStatusViewIfNeed()
        mHolder?.showLoadFailed()
    }

    fun showEmpty() {
        initLoadingStatusViewIfNeed()
        mHolder?.showEmpty()
    }
}