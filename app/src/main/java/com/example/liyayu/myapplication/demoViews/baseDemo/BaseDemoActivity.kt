package com.example.liyayu.myapplication.demoViews.baseDemo

import android.content.Intent
import android.os.Bundle
import com.example.liyayu.myapplication.R
import com.example.liyayu.myapplication.baseFramework.BaseKotlinActivity
import kotlinx.android.synthetic.main.activity_base_demo.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by liyayu on 2018/11/8.
 */
class BaseDemoActivity : BaseKotlinActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_demo)
    }

    override fun initView() {
        super.initView()
        share.onClick {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_TEXT,"test send text")
            shareIntent.type = "text/plain"
            startActivity(Intent.createChooser(shareIntent,"share to"))
        }
    }
}
