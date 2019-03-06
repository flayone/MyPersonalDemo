package com.example.liyayu.myapplication.fragment

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
class ContextFragment : KotlinFragment() {

    var pos: String = ""


    companion object {
        @JvmStatic
        fun newInstance(pos: String): ContextFragment =
                ContextFragment().apply {
                    this.pos = pos
                }
    }

    //    val textId by lazy { getRootView().text_frg }
    override fun initView() {
        LogUtil.d(pos + "11111" + mRootView.toString())
        if (pos.contains("tab")) {
            mRootView.text_frg.text = pos
        } else {
            mRootView.text_frg.text = "输入的文字：$pos"
        }
    }

    override fun initRootView(inflater: LayoutInflater?): View {
        return inflater!!.inflate(R.layout.fragment_context, null)
    }


}