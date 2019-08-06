package com.example.liyayu.myapplication.fragment

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * <pre>
 *     author : JX
 *     e-mail : liyayu@ffde.com
 *     time   : 2019/08/05
 *     desc   :
 *     version: 1.0
 * </pre>
 */

abstract class BaseFragment(@LayoutRes val res: Int) : Fragment() {
    private lateinit var mRootView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mRootView = inflater.inflate(res, container,false)
        mRootView.isClickable = true
        return mRootView
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    abstract fun initView()
}