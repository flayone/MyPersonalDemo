package com.example.liyayu.myapplication.demoViews.loading_demo

import android.os.Bundle
import com.example.liyayu.myapplication.R
import com.example.liyayu.myapplication.demoViews.loading_demo.pages.ActivityLoadingActivity
import com.example.liyayu.myapplication.demoViews.loading_demo.pages.CustomLoadingActivity
import com.example.liyayu.myapplication.demoViews.loading_demo.pages.FragmentLoadingActivity
import com.example.liyayu.myapplication.demoViews.loading_demo.pages.RecycleViewItemActivity
import kotlinx.android.synthetic.main.activity_test_loading.*

/**
 * <pre>
 *     author : JX
 *     e-mail : liyayu@ffde.com
 *     time   : 2019/08/02
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class TestLoadingActivity :BaseLoadingActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_loading)
        btn_atl_activity.setOnClickListener { startAct(ActivityLoadingActivity()) }
        btn_atl_custom.setOnClickListener { startAct(CustomLoadingActivity()) }
        btn_atl_fragment.setOnClickListener { startAct(FragmentLoadingActivity()) }
        btn_atl_item_view.setOnClickListener { startAct(RecycleViewItemActivity()) }
    }
}