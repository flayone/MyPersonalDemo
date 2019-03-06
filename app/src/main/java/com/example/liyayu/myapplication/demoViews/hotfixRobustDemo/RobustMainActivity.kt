package com.example.liyayu.myapplication.demoViews.hotfixRobustDemo

import android.os.Bundle
import com.example.liyayu.myapplication.R
import com.example.liyayu.myapplication.baseFramework.BaseKotlinActivity
import com.example.liyayu.myapplication.util.createRustDir
import com.example.liyayu.myapplication.util.loadPatch
import kotlinx.android.synthetic.main.activity_robust_main.*


/**
 * Created by liyayu on 2018/4/25.
 * 热更新主页
 */
class RobustMainActivity : BaseKotlinActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_robust_main)
    }

    override fun initView() {
        super.initView()
        hot_intent_btn.setOnClickListener {
            startAct(RobustShowActivity())
            // or下面代码同等含义
            // startActivity<RobustShowActivity>()
        }
        hot_get_jar_btn.setOnClickListener {
            loadPatch(this)
        }
        create_dir_btn.setOnClickListener {
            createRustDir(this, dir_name.text.toString()+"/test.text")
        }
    }
}