package com.example.liyayu.myapplication.util

import android.content.Context
import android.net.ConnectivityManager
import android.os.Environment
import java.io.File


/**
 * Created by liyayu on 2018/5/22.
 */

var REQUEST_CODE_SDCARD_READ = 1
var RobustDirName = "HotFix"
var RobustPatchName = "patch.jar"

var file: File? = null
private var count: Int = 0

//网络是否连接
fun isConnected(context: Context): Boolean {
    val conn = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val info = conn.activeNetworkInfo
    return info != null && info.isConnected
}


//创建热更新需要的文件夹。保证唯一性，一个app里只存在一个File文件夹
@Synchronized
fun createRustDir(context: Context, name: String): File {
    if (file == null) {
        try {
            file = File(getPathString(context) + name)
        } catch (e: java.lang.Exception) {
            LogUtil.d(e.toString())
        }
    }
    val parent: File = file!!.parentFile
    if (!parent.exists() || !parent.isDirectory) {
        parent.mkdirs()// 创建文件夹
        count += 10
    }
    LogUtil.d("error-hotfix", count.toString() + " ==>file地址=" + file.toString() + " ==>parentfile地址=" + parent.toString() + "-->" + file!!.absolutePath)
    return file!!
}

fun getPathString(context: Context): String {
    val path = StringBuilder()
    if (isSDAvailable()) {
        path.append(Environment.getExternalStorageDirectory()
                .path)
        path.append(File.separator)
        path.append("AppDirLiYaYu")
//        path.append(File.separator)
//        path.append(dirName)// /mnt/sdcard/AppDirLiYaYu/HotFix

        LogUtil.d("error-hotfix", "如果SD卡可用就在SD卡创建")
    } else {
        //如果SD卡不可用就在内存创建
        val filesDir = context.cacheDir    //  cache  getFileDir file
        path.append(filesDir.absolutePath)

        LogUtil.d("error-hotfix", "SD卡不可用就在内存创建")
    }
    return path.toString()
}

private fun isSDAvailable(): Boolean {
    return if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
        LogUtil.d("hotfix", "sd 有效")
        true
    } else {
        false
    }
}
var add = { x:Int,y:Int ,z:Int->x+y+z }

val addResult = add(1,2,3)

fun printAdd() {
    println("add ==="+add(1,2,3))
}