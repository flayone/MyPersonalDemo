package com.example.liyayu.myapplication.demoViews.hotfixRobustDemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import com.example.liyayu.myapplication.R
import com.example.liyayu.myapplication.baseFramework.BaseKotlinActivity
import com.example.liyayu.myapplication.util.LogUtils
import com.meituan.robust.PatchExecutor
import kotlinx.android.synthetic.main.activity_robust_main.*
import java.io.File



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
//        val file = getDir("test_getDir", Context.MODE_PRIVATE)//内部存储，不建议存储数据，建议用外部存储用来存储数据
//        LogUtils.d("file_path -----"+file.absolutePath)//结果为-/data/user/0/com.example.liyayu.myapplication/app_test_getDir
//        val file = createRustDir() ////创建文件夹
        DownloadPatchManger(this,"http://s1.cximg.com/downloads/cxj/apk/cxj-homes-prd-v1.3.2-20180420.apk").doDownloadThread()
    }

    @SuppressLint("SetTextI18n")
    private fun createRustDir(): File {
        val path = StringBuilder()
        if (isSDAvailable()) {
            path.append(Environment.getExternalStorageDirectory()
                    .path)
            path.append(File.separator)// '/'
            path.append("HotFix")// /mnt/sdcard/HotFix

            LogUtils.d("error-hotfix", "如果SD卡可用就在SD卡创建")
            button9.text = "SD卡可用就在sd创建"
        } else {
            //如果SD卡不可用就在内存创建
            val filesDir = application.cacheDir    //  cache  getFileDir file
            path.append(filesDir.absolutePath)

            button9.text = "SD卡不可用就在内存创建"
            LogUtils.d("error-hotfix", "SD卡不可用就在内存创建")
        }
        val file = File(path.toString())
        if (!file.exists() || !file.isDirectory) {
            file.mkdirs()// 创建文件夹
            count += 10
        }
        Toast.makeText(this, "file=" + file, Toast.LENGTH_SHORT).show()
        LogUtils.d("error-hotfix", count.toString() + " ==>file地址=" + file.toString() + "-->" + file.absolutePath)
        button10.text = ("文件夹位置："+ "\n" + file.toString() + "\n" + file.absolutePath)
        return file
    }

    private fun isSDAvailable(): Boolean {
        return if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            Toast.makeText(this, "sd 有效", Toast.LENGTH_SHORT).show()
            true
        } else {
            false
        }
    }

    private fun isGrantSDCardReadPermission(): Boolean {
        return PermissionUtils.isGrantSDCardReadPermission(this)
    }

    private fun requestPermission() {
        PermissionUtils.requestSDCardReadPermission(this, REQUEST_CODE_SDCARD_READ)
    }

    private val REQUEST_CODE_SDCARD_READ = 1

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