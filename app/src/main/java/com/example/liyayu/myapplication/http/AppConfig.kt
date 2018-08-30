package com.example.liyayu.myapplication.http

import com.example.liyayu.myapplication.baseFramework.BaseApplication
import com.example.liyayu.myapplication.util.LogUtil
import com.example.liyayu.myapplication.util.MyRootName
import com.example.liyayu.myapplication.util.getPathString
import com.yanzhenjie.kalle.util.IOUtils
import java.io.File

/**
 * Created by liyayu on 2018/8/21.
 * 文件目录管理
 */
object AppConfig {
//    private val mPreferences: SharedPreferences
    /**
     * App root path.
     */
    private val PATH_APP_ROOT_NAME : String = MyRootName
    val PATH_APP_ROOT: String = getPathString(BaseApplication.instance.applicationContext) + File.separator + PATH_APP_ROOT_NAME
    /**
     * Download.
     */
    val PATH_APP_DOWNLOAD: String = PATH_APP_ROOT + File.separator + "Download"
    /**
     * Images.
     */
    val PATH_APP_IMAGE: String= PATH_APP_ROOT + File.separator + "Images"
    /**
     * Cache root path.
     */
    val PATH_APP_CACHE: String= PATH_APP_ROOT + File.separator + "Cache"
    fun initFileDir(){
        IOUtils.createFolder(PATH_APP_ROOT)
        IOUtils.createFolder(PATH_APP_DOWNLOAD)
        IOUtils.createFolder(PATH_APP_IMAGE)
        IOUtils.createFolder(PATH_APP_CACHE)
        LogUtil.d("PATH_APP_ROOT= $PATH_APP_ROOT")
    }
}