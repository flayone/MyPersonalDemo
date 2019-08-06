package com.example.liyayu.myapplication.demoViews.loading_demo

import android.view.View

/**
 * <pre>
 *     author : JX
 *     e-mail : liyayu@ffde.com
 *     time   : 2019/08/02
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class GlobalAdapter : Gloading.Adapter {
    override fun getView(holder: Gloading.Holder, convertView: View?, status: Int): View {
        var loadingStatusView: GlobalLoadingStatusView? = null
        //reuse the old view, if possible
        if (convertView != null && convertView is GlobalLoadingStatusView) {
            loadingStatusView = convertView
        }
        if (loadingStatusView == null) {
            loadingStatusView = GlobalLoadingStatusView(holder.context, holder.retryTask)
        }
        loadingStatusView.setStatus(status)
        val data :Any?= holder.getData()
        //show or not show msg view
        val hideMsgView = HIDE_LOADING_STATUS_MSG == data
        loadingStatusView.setMsgViewVisibility(!hideMsgView)
        return loadingStatusView
    }
}