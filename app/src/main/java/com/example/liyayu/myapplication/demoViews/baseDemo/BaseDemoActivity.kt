package com.example.liyayu.myapplication.demoViews.baseDemo

import android.content.Intent
import android.os.Bundle
import com.example.liyayu.myapplication.R
import com.example.liyayu.myapplication.baseFramework.BaseKotlinActivity
import com.example.liyayu.myapplication.customWidgets.OnDragTouchListener
import com.example.liyayu.myapplication.util.BaseBooleanListener
import com.example.liyayu.myapplication.util.BaseClickListener
import com.example.liyayu.myapplication.util.animateRolling
import com.example.liyayu.myapplication.util.showToast
import kotlinx.android.synthetic.main.activity_base_demo.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by liyayu on 2018/11/8.
 */
class BaseDemoActivity : BaseKotlinActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_demo)
    }

    override fun initView() {
        super.initView()
        share.onClick {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_TEXT, "test send text")
            shareIntent.type = "text/plain"
            startActivity(Intent.createChooser(shareIntent, "share to"))
        }
        rolling.onClick {
            animateRolling(rolling, this@BaseDemoActivity)
        }
        irr.setOnSelectedListener(object : BaseBooleanListener {
            override fun onClick(b: Boolean) {
                if (b) {
                    showToast("zuo")
                } else {
                    showToast("you")
                }
            }
        })
        drag.setOnTouchListener(object : OnDragTouchListener(false) {})
        drag.onClick {
            showToast("drag  onClick")
        }
        drag_view.setOnClickListener(object : BaseClickListener {
            override fun onClick() {
                showToast("drag_view  onClick")
            }
        })
    }
}
