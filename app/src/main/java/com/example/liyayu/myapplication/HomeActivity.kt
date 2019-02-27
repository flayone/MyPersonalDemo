package com.example.liyayu.myapplication

import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.example.liyayu.myapplication.baseFramework.BaseKotlinActivity
import com.example.liyayu.myapplication.demoViews.baseDemo.BaseDemoActivity
import com.example.liyayu.myapplication.demoViews.bluetoothPrinterDemo.BluetoothActivity
import com.example.liyayu.myapplication.demoViews.coordinatorlayoutDemo.CoordinatorLayoutActivity
import com.example.liyayu.myapplication.demoViews.fontDemo.FontActivity
import com.example.liyayu.myapplication.demoViews.hotfixRobustDemo.RobustMainActivity
import com.example.liyayu.myapplication.demoViews.imageViewTintDemo.TestImgTintActivity
import com.example.liyayu.myapplication.demoViews.kalle_demo.KalleLoginActivity
import com.example.liyayu.myapplication.demoViews.recycleDemo.RecycleActivity
import com.example.liyayu.myapplication.demoViews.rippleDemo.Coloring
import com.example.liyayu.myapplication.demoViews.rippleDemo.RippleActivity
import com.example.liyayu.myapplication.demoViews.transitionDemo.TransitionMainActivity
import com.example.liyayu.myapplication.jpush.setJpushAlias
import com.example.liyayu.myapplication.util.getVersionName
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by liyayu on 2018/11/8.
 * 首页
 */
class HomeActivity : BaseKotlinActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setJpushAlias(this, getVersionName(this))
        Coloring.get().setViewRipple(text2, button0, button1, button2, button4, button5, button6, button7
                , button8, button9, button10, button_end)

        setStatusColor(R.color.y_bg)
        text1.onClick {
            AlertDialog.Builder(this@HomeActivity)
                    .setTitle("Title")
                    .setMessage("This is message")
                    .setNegativeButton("Cancel") { dialog, which -> }
                    .setPositiveButton("Confirm") { dialog, which -> }
                    .create()
                    .show()
        }
        text2.onClick { startAct(FontActivity()) }
        button_end.onClick { startAct(MainActivity()) }
        button0.onClick { startAct(BaseDemoActivity()) }
        button1.onClick {
            startAct(RecycleActivity())
        }
        button2.onClick {
            startAct(RippleActivity())
        }
        button4.onClick {
            startAct(TransitionMainActivity())
        }
        button5.onClick {
            startAct(TestImgTintActivity())
        }
        button6.onClick {
            startAct(com.example.liyayu.myapplication.demoViews.bigImgDemo.MainActivity())
        }
        button7.onClick {
            startAct(CoordinatorLayoutActivity())
        }
        button8.onClick {
            startAct(RobustMainActivity())
        }
        button9.onClick {
            startAct(BluetoothActivity())
        }
        button10.onClick {
            startAct(KalleLoginActivity())
        }

    }
}