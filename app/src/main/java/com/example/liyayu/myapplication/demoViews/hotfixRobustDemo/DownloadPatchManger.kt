package com.example.liyayu.myapplication.demoViews.hotfixRobustDemo

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Message
import android.widget.Toast
import com.example.liyayu.myapplication.baseFramework.BaseHandler
import com.example.liyayu.myapplication.util.*
import com.meituan.robust.PatchExecutor
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL


/**
 * Created by liyayu on 2018/5/7.
 * 下载热更新所需文件到本地文件夹
 */
class DownloadPatchManger private constructor(context: Activity, downloadUrl: String, dirName: String, fileName: String) {
    //单例模式
    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: DownloadPatchManger? = null

        fun getInstance(context: Activity, downloadUrl: String = "", dirName: String = RobustDirName, fileName: String = RobustPatchName): DownloadPatchManger {
            if (instance == null) {
                synchronized(DownloadPatchManger::class) {
                    if (instance == null) {
                        instance = DownloadPatchManger(context, downloadUrl, dirName, fileName)
                    }
                }
            }
            return instance!!
        }
    }

    private var activity: Activity = context
    private val interceptFlag = false//是否取消下载

    private val DOWN_UPDATE = 1
    private val DOWN_OVER = 2
    private var downLoadThread: Thread? = null
    //创建对应的文件夹目录
    private var robustDir: File? = createRustDir(context,"/$dirName/$fileName")

    fun doDownloadThread() {
        downLoadThread = Thread(downloadRunnable)
        downLoadThread!!.start()
    }

    private var downloadRunnable: Runnable = Runnable {
        try {
            //可自动关闭方法 = Java中自动关闭流.try-with-resource
            FileOutputStream(robustDir).use { fos ->
                val conn = URL(downloadUrl).openConnection() as HttpURLConnection
                conn.connect()
                val `is` = conn.inputStream

                val buf = ByteArray(1024)

                do {
                    val numread = `is`.read(buf)
                    // 更新进度
                    mHandler.sendEmptyMessage(DOWN_UPDATE)
                    if (numread <= 0) {
                        // 下载完成通知
                        mHandler.sendEmptyMessage(DOWN_OVER)
                        break
                    }
                    fos.write(buf, 0, numread)
                } while (!interceptFlag)// 点击取消就停止下载.
                `is`.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            LogUtil.d("hotfix", e.toString())
        }
    }

    private val mHandler =
            @SuppressLint("HandlerLeak")
            object : BaseHandler(context) {
                override fun baseHandleMessage(msg: Message) {
                    super.baseHandleMessage(msg)
                    when (msg.what) {
                        DOWN_UPDATE -> {
                            LogUtil.d("hotfix", "下载中")
                        }
                        DOWN_OVER -> {
                            LogUtil.d("hotfix", "下载完成")
                            stopDownloadThread()
                            loadPatch()
                        }
                        else -> {
                        }
                    }
                }

            }

    //加载插件包
    private fun loadPatch() {
        if (isGrantSDCardReadPermission()) {
            runRobust()
        } else {
            requestPermission()
        }
    }


    private fun isGrantSDCardReadPermission(): Boolean {
        return PermissionUtils.isGrantSDCardReadPermission(activity)
    }

    private fun requestPermission() {
        PermissionUtils.requestSDCardReadPermission(activity, REQUEST_CODE_SDCARD_READ)
    }

    fun handlePermissionResult() {
        if (isGrantSDCardReadPermission()) {
            runRobust()
        } else {
            Toast.makeText(activity, "failure because without sd card read permission", Toast.LENGTH_SHORT).show()
        }

    }

    private fun runRobust() {
        PatchExecutor(activity.applicationContext, PatchManipulateImp(), RobustCallBackSample()).start()
    }

    fun stopDownloadThread() {
        if (downLoadThread!!.isAlive) {
            downLoadThread!!.interrupt()
            LogUtil.d("hotfix", "downLoadThread isAlive==" + downLoadThread!!.isAlive)
        }
    }


}