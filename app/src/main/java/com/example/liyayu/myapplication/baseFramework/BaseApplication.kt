package com.example.liyayu.myapplication.baseFramework

import android.app.Application
import cn.jpush.android.api.JPushInterface
import com.example.liyayu.myapplication.BuildConfig

/**
 * Created by liyayu on 2018/5/22.
 */
class BaseApplication : Application() {
     var Debug  = BuildConfig.DEBUG
     //单例模式
     companion object {
          lateinit var instance: BaseApplication //延迟加载，不需要初始化，否则需要在构造函数初始化
               private set
     }

     override fun onCreate() {
          super.onCreate()
          instance = this
          if (Debug){
               JPushInterface.setDebugMode(true) 	// 设置开启日志,发布时请关闭日志
          }
          JPushInterface.init(this)           // 初始化 JPush
     }
}