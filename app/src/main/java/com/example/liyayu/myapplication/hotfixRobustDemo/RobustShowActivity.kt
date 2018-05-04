package com.example.liyayu.myapplication.hotfixRobustDemo

import android.os.Bundle
import com.example.liyayu.myapplication.BaseKotlinActivity
import com.example.liyayu.myapplication.R
import com.meituan.robust.patch.annotaion.Add
import com.meituan.robust.patch.annotaion.Modify
import kotlinx.android.synthetic.main.activity_base_empty.*

/**
 * Created by liyayu on 2018/4/26.
 */
class RobustShowActivity :BaseKotlinActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_empty)
    }


    @Modify
    override fun initView() {
        super.initView()
        getGText()
        getRustText()
    }
    private fun getGText(){
        text_base.text = "Hello_empty_world"
    }

    @Add
    private fun getRustText(){
        text_base.text = "我这是改过了的，热更新有效果啦"
    }

}