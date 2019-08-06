package com.example.liyayu.myapplication.demoViews.loading_demo.pages

import android.os.Bundle
import com.example.liyayu.myapplication.R
import com.example.liyayu.myapplication.demoViews.loading_demo.BaseLoadingActivity

/**
 * <pre>
 *     author : JX
 *     e-mail : liyayu@ffde.com
 *     time   : 2019/08/05
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class FragmentLoadingActivity :BaseLoadingActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_fragment_frame)
        replaceFragment(R.id.fl_root, LoadingFragment())
    }
}