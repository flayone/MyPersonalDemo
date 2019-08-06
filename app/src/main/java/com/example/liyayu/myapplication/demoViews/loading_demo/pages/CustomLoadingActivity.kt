package com.example.liyayu.myapplication.demoViews.loading_demo.pages

import com.example.liyayu.myapplication.demoViews.loading_demo.Gloading
import com.example.liyayu.myapplication.demoViews.loading_demo.SpecialAdapter

/**
 * <pre>
 *     author : JX
 *     e-mail : liyayu@ffde.com
 *     time   : 2019/08/05
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class CustomLoadingActivity : ActivityLoadingActivity() {
    init {
        loadingTime = 3000
    }
    override fun initLoadingStatusViewIfNeed() {
        if (mHolder == null) {
            val customL = Gloading.from(SpecialAdapter())
            mHolder = customL.wrap(this).withRetry {
                onLoadRetry()
            }
        }
    }
}