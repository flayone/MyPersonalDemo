package com.example.liyayu.myapplication.demoViews.hotfixRobustDemo

import android.annotation.SuppressLint
import android.content.Context
import android.os.Message
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
class DownloadPatchManger private constructor(private var context: Context, downloadUrl: String, dirName: String, fileName: String) {
    //单例模式
    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: DownloadPatchManger? = null

        fun getInstance(context: Context, downloadUrl: String = "", dirName: String = RobustDirName, fileName: String = RobustPatchName): DownloadPatchManger {
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

    private val interceptFlag = false//是否取消下载

    private val DOWN_UPDATE = 1
    private val DOWN_OVER = 2
    private var downLoadThread: Thread? = null
    //创建对应的文件夹目录
    private var robustDir: File? = createRustDir(context, "$dirName/$fileName")

    var loc = 0
    private var progress = 0
    private var isDownloading = false

    fun doDownloadThread() {
        if(isDownloading){
            ToastUtil.showToast(context,"正在准备中……")
            return
        }
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

                var len = 0
                val buf = ByteArray(1024)

                do {
                    val numread = `is`.read(buf)
                    // 更新进度
                    mHandler.sendEmptyMessage(DOWN_UPDATE)
                    len += numread
                    progress = len*100/conn.contentLength
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
            LogUtil.d("RobustHotfix", e.toString())
        }
    }
    private val mHandler =
            @SuppressLint("HandlerLeak")
            object : BaseHandler(context) {
                override fun baseHandleMessage(msg: Message) {
                    super.baseHandleMessage(msg)
                    when (msg.what) {
                        DOWN_UPDATE -> {
                            isDownloading = true
                            if(loc != progress){
                                loc = progress
                                LogUtil.d("RobustHotfix", "下载中 $loc %")
                            }
                        }
                        DOWN_OVER -> {
                            isDownloading = false
                            LogUtil.d("RobustHotfix", "下载完成")
                            stopDownloadThread()
                            runRobust()
                        }
                        else -> {
                        }
                    }
                }

            }

    /** 调用热更新方法 加载插件包*/
    private fun runRobust() {
        PatchExecutor(context.applicationContext, PatchManipulateImp(), RobustCallBackSample()).start()
    }

    fun stopDownloadThread() {
        if (downLoadThread!!.isAlive) {
            downLoadThread!!.interrupt()
            LogUtil.d("RobustHotfix", "downLoadThread isAlive==" + downLoadThread!!.isAlive)
        }
    }
}