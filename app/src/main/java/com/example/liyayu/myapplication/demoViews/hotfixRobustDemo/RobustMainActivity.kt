package com.example.liyayu.myapplication.demoViews.hotfixRobustDemo

import android.os.Bundle
import android.widget.Toast
import com.example.liyayu.myapplication.R
import com.example.liyayu.myapplication.baseFramework.BaseKotlinActivity
import com.example.liyayu.myapplication.util.REQUEST_CODE_SDCARD_READ
import com.example.liyayu.myapplication.util.createRustDir
import com.meituan.robust.PatchExecutor
import kotlinx.android.synthetic.main.activity_robust_main.*


/**
 * Created by liyayu on 2018/4/25.
 * 热更新主页
 */
class RobustMainActivity : BaseKotlinActivity() {
    var count :Int =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_robust_main)
    }

    override fun initView() {
        super.initView()
//        add(1,2,3)
        hot_intent_btn.setOnClickListener {
            startAct(RobustShowActivity::class.java)
        }
        hot_get_jar_btn.setOnClickListener({
            if (isGrantSDCardReadPermission()) {
                runRobust()
            } else {
                requestPermission()
            }
        })
        create_dir_btn.setOnClickListener{
            createRustDir(this,dir_name.text.toString())
        }

//        val file = getDir("test_getDir", Context.MODE_PRIVATE)//内部存储，不建议存储数据，建议用外部存储用来存储数据
//        LogUtil.d("file_path -----"+file.absolutePath)//结果为-/data/user/0/com.example.liyayu.myapplication/app_test_getDir
//        val file = createRustDir() ////创建文件夹
        DownloadPatchManger.getInstance(this,"http://s1.cximg.com/downloads/cxj/apk/cxj-homes-prd-v1.3.2-20180420.apk").doDownloadThread()
    }


    private fun isGrantSDCardReadPermission(): Boolean {
        return PermissionUtils.isGrantSDCardReadPermission(this)
    }

    private fun requestPermission() {
        PermissionUtils.requestSDCardReadPermission(this, REQUEST_CODE_SDCARD_READ)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_SDCARD_READ -> handlePermissionResult()

            else -> {
            }
        }
    }

    private fun handlePermissionResult() {
        if (isGrantSDCardReadPermission()) {
            runRobust()
        } else {
            Toast.makeText(this, "failure because without sd card read permission", Toast.LENGTH_SHORT).show()
        }

    }

    private fun runRobust() {
        PatchExecutor(applicationContext, PatchManipulateImp(), RobustCallBackSample()).start()
    }
}