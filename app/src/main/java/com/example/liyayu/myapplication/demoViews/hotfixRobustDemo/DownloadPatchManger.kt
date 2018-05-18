package com.example.liyayu.myapplication.demoViews.hotfixRobustDemo

import android.app.Activity
import android.content.Context
import android.os.Environment
import android.os.Message
import com.example.liyayu.myapplication.baseFramework.BaseHandler
import com.example.liyayu.myapplication.util.LogUtils
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


/**
 * Created by liyayu on 2018/5/7.
 * 下载热更新所需文件到本地文件夹
 */
class DownloadPatchManger(context: Activity, downloadUrl: String, private var dirName: String = "app", fileName: String = "homes.apk") {
    var downloadPath: String = ""
        get() = file!!.absolutePath
    var count: Int = 0
    var file: File? = null
    private val interceptFlag = false

    private val DOWN_UPDATE = 1
    private val DOWN_OVER = 2
    private var downLoadThread: Thread? = null
    //    private var dirName :String = "app"//"HotFix"
    private var robustDir: File? = createRustDir(context, "/" + fileName)

    fun doDownloadThread() {
        downLoadThread = Thread(downloadRunnable)
        downLoadThread!!.start()
    }

    private var downloadRunnable: Runnable = Runnable {
        var fos: FileOutputStream? = null
        try {
            val url = URL(downloadUrl)

            val conn = url
                    .openConnection() as HttpURLConnection
            conn.connect()
//            val length = conn.contentLength
            val `is` = conn.inputStream

            fos = FileOutputStream(robustDir)

            var count = 0
            val buf = ByteArray(1024)

            do {
                val numread = `is`.read(buf)
                count += numread
//                progress = (count.toFloat() / length * 100).toInt()
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
        } catch (e: Exception) {
            e.printStackTrace()
            LogUtils.d("hotfix", e.toString())
        } finally {
            try {
                if (fos != null) {
                    fos.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                LogUtils.d("hotfix", e.toString())
            }

        }
    }

    private val mHandler =
            object : BaseHandler(context) {
                override fun baseHandleMessage(msg: Message) {
                    super.baseHandleMessage(msg)
                    when (msg.what) {
                        DOWN_UPDATE -> {
                            LogUtils.d("hotfix", "下载中")
                        }
                        DOWN_OVER -> {
                            LogUtils.d("hotfix", "下载完成")
                            stopDownloadThread()
                        }
                        else -> {
                        }
                    }
                }

            }

    fun stopDownloadThread(){
        if (downLoadThread!!.isAlive) {
            downLoadThread!!.interrupt()
            LogUtils.d("hotfix", "downLoadThread isAlive==" + downLoadThread!!.isAlive)
        }
    }

    //保证唯一性，一个app里只存在一个File文件夹
    @Synchronized internal fun createRustDir(context: Context, name: String): File {
        if (file == null) {
            try {
                file = File(getPathString(context) + name)
            } catch (e: java.lang.Exception) {
                LogUtils.d(e.toString())
            }
        }
        var parent: File = file!!.parentFile
        if (!parent.exists() || !parent.isDirectory) {
            parent.mkdirs()// 创建文件夹
            count += 10
        }
        LogUtils.d("error-hotfix", count.toString() + " ==>file地址=" + file.toString() + " ==>parentfile地址=" + parent.toString() + "-->" + file!!.absolutePath)
        return file!!
    }

    private fun getPathString(context: Context): String {
        val path = StringBuilder()
        if (isSDAvailable()) {
            path.append(Environment.getExternalStorageDirectory()
                    .path)
            path.append(File.separator)
            path.append("AppDirLiYaYu")
            path.append(File.separator)
            path.append(dirName)// /mnt/sdcard/AppDirLiYaYu/HotFix

            LogUtils.d("error-hotfix", "如果SD卡可用就在SD卡创建")
        } else {
            //如果SD卡不可用就在内存创建
            val filesDir = context.cacheDir    //  cache  getFileDir file
            path.append(filesDir.absolutePath)

            LogUtils.d("error-hotfix", "SD卡不可用就在内存创建")
        }
        return path.toString()
    }

    private fun isSDAvailable(): Boolean {
        return if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            LogUtils.d("hotfix", "sd 有效")
            true
        } else {
            false
        }
    }

}