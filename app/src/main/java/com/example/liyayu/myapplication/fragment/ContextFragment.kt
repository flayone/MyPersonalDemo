package com.example.liyayu.myapplication.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import com.example.liyayu.myapplication.R
import com.example.liyayu.myapplication.baseFramework.KotlinFragment
import com.example.liyayu.myapplication.util.LogUtil
import kotlinx.android.synthetic.main.fragment_context.view.*


/**
 * Created by liyayu on 2018/3/20.
 * 简单的只带有context文字的fragment
 */
@SuppressLint("ValidFragment")
class ContextFragment(private val pos: String = "") : KotlinFragment() {
    //    val textId by lazy { getRootView().text_frg }
    override fun initView() {
        LogUtil.d(pos + "11111" + mRootView.toString())
        mRootView.text_frg.text = pos
    }

    override fun initRootView(inflater: LayoutInflater?): View {
        return inflater!!.inflate(R.layout.fragment_context, null)
    }


}