package com.example.liyayu.myapplication.demoViews.baseDemo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.example.liyayu.myapplication.R
import com.example.liyayu.myapplication.baseFramework.BaseKotlinActivity
import com.example.liyayu.myapplication.customWidgets.OnDragListener
import com.example.liyayu.myapplication.dialog.BaseImageDialog
import com.example.liyayu.myapplication.util.*
import kotlinx.android.synthetic.main.activity_base_demo.*
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
 * Created by liyayu on 2018/11/8.
 */
class BaseDemoActivity : BaseKotlinActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_demo)
        setStatusColor(R.color.background_light_white, true)
    }

    override fun initView() {
        super.initView()

        iv_avau_img.visibility = View.GONE
        share.onClick {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_TEXT, "test send text")
            shareIntent.type = "text/plain"
            startActivity(Intent.createChooser(shareIntent, "share to"))
        }
        rolling.onClick {
            animateRolling(rolling, this@BaseDemoActivity)
            val flag = fill_view.visibility
            if (flag == View.GONE) {
                fill_view.visibility = View.VISIBLE
            } else {
                fill_view.visibility = View.GONE
            }
        }
        irr.setOnSelectedListener(object : BaseBooleanListener {
            override fun onClick(b: Boolean) {
                if (b) {
                    showToast("drag_view 设置为自动贴边")
                } else {
                    showToast("drag_view 设置为不贴边")
                }
                drag_view.isAuto(b)
            }
        })
        var mLeft = 300
        var mTop = 500
        drag.setOnTouchListener(object : OnDragListener(false, object : OnDraggableClickListener {
            override fun onDragged(v: View, left: Int, top: Int) {
                showToast("drag -onDragged [$left,$top]")
                mLeft = left
                mTop = top
            }

            override fun onClick(v: View) {
                showToast("drag -onClick ")
                iv_avau_img.visibility = View.VISIBLE
                startActDrop(iv_avau_img, mLeft + (dragWidth / 2), mTop + (dragHeight / 2), dragWidth.toFloat(), getScreenH().toFloat(), { iv_avau_img.visibility = View.GONE }, 800)
            }

        }) {})

        drag_view.setOnClickListener(object : BaseClickListener {
            override fun onClick() {
                showToast("drag_view  onClick")
            }
        })

        btn_snapshot.onClick {
            BaseImageDialog.Bu(this@BaseDemoActivity, DisplayUtils().shotActivity(this@BaseDemoActivity)).create().show()
        }
        btn_long_snapshot.onClick {
            BaseImageDialog.Bu(this@BaseDemoActivity, DisplayUtils().shotNestScrollView(ns_root)).create().show()
        }
//        drag.layout(0, 200, 200, 250)

        val lp = drag.layoutParams as ViewGroup.MarginLayoutParams
        lp.setMargins(mLeft, mTop, 0, 0)
        drag.layoutParams = lp
    }

    var dragHeight = 0
    var dragWidth = 0


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            dragHeight = drag.height
            dragWidth = drag.width
        }
    }

}
