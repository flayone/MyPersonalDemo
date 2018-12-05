package com.example.liyayu.myapplication.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Environment
import android.view.View
import com.example.liyayu.myapplication.baseFramework.BaseApplication
import com.example.liyayu.myapplication.demoViews.hotfixRobustDemo.DownloadPatchManger
import com.example.liyayu.myapplication.demoViews.hotfixRobustDemo.PatchManipulateImp
import com.example.liyayu.myapplication.demoViews.hotfixRobustDemo.RobustCallBackSample
import com.example.liyayu.myapplication.util.permission.PermissionUtil
import com.meituan.robust.PatchExecutor
import org.jetbrains.anko.displayMetrics
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by liyayu on 2018/5/22.
 */

var REQUEST_CODE_SDCARD_READ = 1
var JPUSH_TAG_CODE = 2
var JPUSH_ALIAS_CODE = 3
var RobustDirName = "RobustHotfix" //热更新文件夹名称
var RobustPatchName = "patch.jar"
var MyRootName = "AppDirLiYaYu" //手机上创建的根目录名称

private var count: Int = 0
var add = { x: Int, y: Int, z: Int -> x + y + z }

val addResult = add(1, 2, 3)
val app: BaseApplication
    get() = BaseApplication.instance

//网络是否连接
fun isConnected(context: Context): Boolean {
    val conn = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val info = conn.activeNetworkInfo
    return info != null && info.isConnected
}

//创建热更新需要的文件夹。并发，保证唯一性
@Synchronized
fun createRustDir(context: Context, name: String): File {
    var file: File? = null
    try {
        file = File(getPathString(context) + "/$name")
        val parent: File = file.parentFile
        if (!parent.exists() || !parent.isDirectory) {
            parent.mkdirs()// 创建文件夹
            count += 10
        }
        LogUtil.d("RobustHotfix", count.toString() + " ==>file地址=" + file.toString() + " ==>parentfile地址=" + parent.toString() + "-->" + file!!.absolutePath)
    } catch (e: java.lang.Exception) {
        LogUtil.d(e.toString())
    }
    return file!!
}

fun getPathString(context: Context): String {
    val path = StringBuilder()
    if (isSDAvailable()) {
        path.append(Environment.getExternalStorageDirectory()
                .path)
        path.append(File.separator)
        path.append(MyRootName)
//        path.append(File.separator)
//        path.append(dirName)// /mnt/sdcard/AppDirLiYaYu/RobustHotfix
        LogUtil.d("RobustHotfix", "如果SD卡可用就在SD卡创建")
    } else {
        //如果SD卡不可用就在内存创建
        val filesDir = context.cacheDir    //  cache  getFileDir file
        path.append(filesDir.absolutePath)

        LogUtil.d("RobustHotfix", "SD卡不可用就在内存创建")
    }
    return path.toString()
}

private fun isSDAvailable(): Boolean {
    return if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
        LogUtil.d("RobustHotfix", "sd 有效")
        true
    } else {
        false
    }
}

fun getPatch(context: Context, url: String) {
    //权限校验
    PermissionUtil.doTaskWithPermissions(context as Activity?
            , "为保证app功能正常，需要存储权限"
            , arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            , object : PermissionUtil.Callback() {
        override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>?) {
            (0 until perms!!.size)
                    .map { perms[it] }
                    .forEach { LogUtil.d("被拒绝的权限:" + it) }
            ToastUtil.showToast(context, "应用存储权限获取被拒绝")
        }

        override fun onAfterAllPermissionGranted(requestCode: Int, perms: MutableList<String>?) {
            LogUtil.d("go" + "DownloadPatchManger:")
            DownloadPatchManger.getInstance(context, url).doDownloadThread()
        }
    })
}

fun loadPatch(context: Context) {
    val file = File(getPathString(context) + File.separator + RobustDirName + File.separator + RobustPatchName)
    if (file.exists()) {
        PatchExecutor(context.applicationContext, PatchManipulateImp(), RobustCallBackSample()).start()
    } else {
//        LogUtil.d("Patch file doesn't exists")
        ToastUtil.showToast(context, "Patch file doesn't exists")
    }
}

/**
 * 获取版本号
 * @return 当前应用的版本号
 */
fun getVersionCode(context: Context): Int {
    try {
        return context.packageManager.getPackageInfo(context.packageName, 0).versionCode
    } catch (e: PackageManager.NameNotFoundException) {
        // e.printStackTrace();
    }
    return 0
}

/**
 * 获取版本名
 * @return 当前应用的版本号
 */
fun getVersionName(context: Context): String {
    try {
        return context.packageManager.getPackageInfo(context.packageName, 0).versionName
    } catch (e: PackageManager.NameNotFoundException) {
        // e.printStackTrace();
    }
    return "未知版本"
}

fun getNowTime(): String {
    val formatter = SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA)
    val curDate = Date(System.currentTimeMillis())//获取当前时间
    return formatter.format(curDate)
}

fun animateRolling(v: View, context: Context) {
    v.alpha = 0f
    v.scaleX = 0.4f

    v.animate()
            .alpha(1f)
            .scaleX(1f)
            .setStartDelay(100)
            .setDuration(1200).interpolator = AnimUtils.getFastOutSlowInInterpolator(context)
}

fun px2dp(px: Float): Int {
    val scale = app.displayMetrics.density
    return (px / scale + 0.5f).toInt()
}

fun dp2px(dp: Float): Int {
    val scale = app.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

fun px2sp(px: Float): Int {
    val scale = app.displayMetrics.scaledDensity
    return (px / scale + 0.5f).toInt()
}

fun sp2px(sp: Float): Int {
    val scale = app.displayMetrics.scaledDensity
    return (sp * scale + 0.5f).toInt()
}


fun showToast(msg: String) {
    ToastUtil.showToast(app, msg)
}

fun LogD(msg: String) {
    LogUtil.d(msg)
}

interface BaseEnsureListener {
    fun ensure(s: String)
}

interface BasePositionListener {
    fun onClick(i: Int)
}

interface BaseBooleanListener {
    fun onClick(i: Boolean)
}

interface BaseClickListener {
    fun onClick()
}