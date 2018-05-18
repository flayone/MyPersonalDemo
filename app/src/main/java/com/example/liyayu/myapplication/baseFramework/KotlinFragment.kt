package com.example.liyayu.myapplication.baseFramework

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.liyayu.myapplication.util.LogUtils


/**
 * Created by liyayu on 2018/3/20.
 */
abstract class KotlinFragment : Fragment() {
    lateinit var mRootView: View

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        LogUtils.d("onCreateView:" + toString())
        mRootView = initRootView(inflater)
        mRootView?.isClickable = true
        return mRootView
    }

    abstract fun initRootView(inflater: LayoutInflater?): View

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    abstract fun initView()
}