package com.example.liyayu.myapplication

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import com.example.liyayu.myapplication.baseFramework.BaseKotlinActivity
import com.example.liyayu.myapplication.baseFramework.BaseRvAdapter
import com.example.liyayu.myapplication.baseFramework.initRecycleLayoutManger
import com.example.liyayu.myapplication.demoViews.baseDemo.BaseDemoActivity
import com.example.liyayu.myapplication.demoViews.bluetoothPrinterDemo.BluetoothActivity
import com.example.liyayu.myapplication.demoViews.coordinatorlayoutDemo.CoordinatorLayoutActivity
import com.example.liyayu.myapplication.demoViews.easy_permissions_demo.EasyPermissionActivity
import com.example.liyayu.myapplication.demoViews.fontDemo.FontActivity
import com.example.liyayu.myapplication.demoViews.hotfixRobustDemo.RobustMainActivity
import com.example.liyayu.myapplication.demoViews.imageViewTintDemo.TestImgTintActivity
import com.example.liyayu.myapplication.demoViews.kalle_demo.KalleLoginActivity
import com.example.liyayu.myapplication.demoViews.loading_demo.TestLoadingActivity
import com.example.liyayu.myapplication.demoViews.recycleDemo.RecycleActivity
import com.example.liyayu.myapplication.demoViews.rippleDemo.Coloring
import com.example.liyayu.myapplication.demoViews.rippleDemo.RippleActivity
import com.example.liyayu.myapplication.demoViews.tools_calculate.CalculateForTools
import com.example.liyayu.myapplication.demoViews.transitionDemo.TransitionMainActivity
import com.example.liyayu.myapplication.jpush.setJpushAlias
import com.example.liyayu.myapplication.util.getVersionName
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.item_main.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by liyayu on 2018/11/8.
 * 首页
 */

//@TestInject(BaseDemoActivity::class)
class HomeActivity : BaseKotlinActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setJpushAlias(this, getVersionName(this))

        setStatusColor(R.color.y_bg)
//        text1.onClick {
//            AlertDialog.Builder(this@HomeActivity)
//                    .setTitle("Title")
//                    .setMessage("This is message")
//                    .setNegativeButton("Cancel") { dialog, which -> }
//                    .setPositiveButton("Confirm") { dialog, which -> }
//                    .create()
//                    .show()
//        }
//        text2.onClick { startAct(FontActivity()) }
//        button_end.onClick { startAct(MainActivity()) }
        val dataArray = mutableListOf(
                GoItem("Loading框架", TestLoadingActivity()),
                GoItem("EasyPermission",EasyPermissionActivity()),
                GoItem("kalle网络框架",KalleLoginActivity()),
                GoItem("自定义控件集合",BaseDemoActivity()),
                GoItem("Robust热更新",RobustMainActivity()),
                GoItem("约束布局探索",CoordinatorLayoutActivity()),
                GoItem("大图模式",com.example.liyayu.myapplication.demoViews.bigImgDemo.MainActivity()),
                GoItem("利用tint属性修改颜色及展示方式",TestImgTintActivity()),
                GoItem("页面转场动画",TransitionMainActivity()),
                GoItem("布局点击涟漪效果",RippleActivity()),
                GoItem("recycleView中添加动画效果",RecycleActivity()),
                GoItem("蓝牙连接打印机demo",BluetoothActivity()),
                GoItem("门窗计算器",CalculateForTools()))

        val adapter = MainItem(dataArray, this)
        rv_ah_list.layoutManager = initRecycleLayoutManger(this)
        rv_ah_list.adapter = adapter
    }

    class MainItem(list: MutableList<GoItem>, val mActivity: BaseKotlinActivity) : BaseRvAdapter<GoItem>(list, R.layout.item_main) {
        override fun onBind(holderView: View, position: Int, data: GoItem) = with(holderView) {
            Coloring.get().setViewRipple(btn_im_show)
            btn_im_show.text = data.title
            btn_im_show.setOnClickListener {
                mActivity.startAct(data.activity ?: BaseDemoActivity())
            }
        }

    }

    data class GoItem(val title: String? = "", val activity: Activity? = BaseDemoActivity())
}