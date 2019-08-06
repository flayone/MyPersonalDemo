package com.example.liyayu.myapplication.demoViews.loading_demo.pages

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.liyayu.myapplication.R
import com.example.liyayu.myapplication.demoViews.loading_demo.*
import kotlinx.android.synthetic.main.activity_recycle_loading.*
import kotlinx.android.synthetic.main.item_recycle_loading.view.*
import java.util.ArrayList

/**
 * <pre>
 *     author : JX
 *     e-mail : liyayu@ffde.com
 *     time   : 2019/08/05
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class RecycleViewItemActivity : BaseLoadingActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_loading)
        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.orientation = OrientationHelper.VERTICAL
        rv_arl_list.layoutManager = layoutManager
        rv_arl_list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rv_arl_list.itemAnimator = DefaultItemAnimator()
        rv_arl_list.adapter = ItemRv(initData())
    }


    private fun initData(): MutableList<String> {
        val size = 20
        val list = ArrayList<String>(size + 4)
        list.add("")//empty
        list.add(getRandomImage())
        list.add(getRandomImage())
        list.add(getErrorImage())//failed
        for (i in 0 until size) {
            list.add(getRandomImage())
        }
        return list
    }


    class ItemRv(list: MutableList<String>) : LoadingRvItemAdapter<String>(list) {
        override fun onCreateViewHolder(parent: ViewGroup): LoadingViewHolder {
            val view = LayoutInflater.from(mContext).inflate(R.layout.item_recycle_loading,parent,false)
            val imgView = view.iv_irl_show
            val holder = Gloading.getDefault().wrap(imgView)
            holder.withData(HIDE_LOADING_STATUS_MSG)
            return LoadingViewHolder(mContext, holder, imgView,view)
        }

        override fun onBind(holderView: LoadingViewHolder, position: Int, data: String) = with(holderView) {
            showImage(data)
        }
    }
}