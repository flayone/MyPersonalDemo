package com.example.liyayu.myapplication.demoViews.hotfixRobustDemo

import android.os.Bundle
import com.example.liyayu.myapplication.R
import com.example.liyayu.myapplication.baseFramework.BaseKotlinActivity
import com.example.liyayu.myapplication.util.createRustDir
import com.example.liyayu.myapplication.util.file
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
            startAct(RobustShowActivity::class.java)
        }
        hot_get_jar_btn.setOnClickListener({
            getPatch()
        })
        create_dir_btn.setOnClickListener {
            file = null
            createRustDir(this, dir_name.text.toString()+"/test.text")
        }
    }
//
//    private fun getPatch() {
//        //权限校验
//        PermissionUtil.doTaskWithPermissions(this@RobustMainActivity
//                , "为保证app功能正常，需要存储权限"
//                , arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
//                , object : PermissionUtil.Callback() {
//            override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>?) {
//                (0..perms!!.size)
//                        .map { perms[it] }
//                        .forEach { LogUtil.d("被拒绝的权限:" + it) }
//                ToastUtil.showToast(this@RobustMainActivity, "应用存储权限获取被拒绝")
//            }
//
//            override fun onAfterAllPermissionGranted(requestCode: Int, perms: MutableList<String>?) {
//                LogUtil.d("go" +
//                        "DownloadPatchManger:")
//                DownloadPatchManger.getInstance(this@RobustMainActivity, "http://s1.cximg.com/downloads/cxj/apk/cxj-homes-prd-v1.3.2-20180420.apk").doDownloadThread()
//            }
//        })
//    }

//    private fun isGrantSDCardReadPermission(): Boolean {
//        return PermissionUtils.isGrantSDCardReadPermission(this)
//    }
//
//    private fun requestPermission() {
//        PermissionUtils.requestSDCardReadPermission(this, REQUEST_CODE_SDCARD_READ)
//    }
//
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        when (requestCode) {
//            REQUEST_CODE_SDCARD_READ -> handlePermissionResult()
//
//            else -> {
//            }
//        }
//    }
//
//    private fun handlePermissionResult() {
//        if (isGrantSDCardReadPermission()) {
//            runRobust()
//        } else {
//            Toast.makeText(this, "failure because without sd card read permission", Toast.LENGTH_SHORT).show()
//        }
//
//    }
//
//    private fun runRobust() {
//        PatchExecutor(applicationContext, PatchManipulateImp(), RobustCallBackSample()).start()
//    }
}