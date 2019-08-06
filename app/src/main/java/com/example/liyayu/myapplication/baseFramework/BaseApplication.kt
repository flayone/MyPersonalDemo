package com.example.liyayu.myapplication.baseFramework

import android.app.Activity
import android.app.Application
import cn.jpush.android.api.JPushInterface
import com.example.liyayu.myapplication.BuildConfig
import com.example.liyayu.myapplication.R
import com.example.liyayu.myapplication.demoViews.loading_demo.Gloading
import com.example.liyayu.myapplication.demoViews.loading_demo.GlobalAdapter
import com.example.liyayu.myapplication.http.AppConfig
import com.example.liyayu.myapplication.http.MyConverter
import com.example.liyayu.myapplication.http.OkHttpConFactory
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.yanzhenjie.kalle.Kalle
import com.yanzhenjie.kalle.KalleConfig
import com.yanzhenjie.kalle.connect.BroadcastNetwork
import com.yanzhenjie.kalle.connect.http.LoggerInterceptor
import com.yanzhenjie.kalle.cookie.DBCookieStore
import com.yanzhenjie.kalle.simple.cache.DiskCacheStore
import okhttp3.OkHttpClient


/**
 * Created by liyayu on 2018/5/22.
 */
class BaseApplication : Application() {
     var Debug  = BuildConfig.DEBUG
     private var mCurrentActivity: Activity? = null
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
          AppConfig.initFileDir() //创建所需文件夹
          Kalle.setConfig(KalleConfig.newBuilder()//定义Kalle网络框架全局属性
                  .connectFactory(OkHttpConFactory(OkHttpClient.Builder().build()))//默认调用URLConnectionFactory，这里自定义调用的okhttp3
                  .cookieStore(DBCookieStore.newBuilder(this).build())//用到Cookie时才设置
                  .cacheStore(DiskCacheStore.newBuilder(AppConfig.PATH_APP_CACHE).password("666").build())//对于网络缓存的操作，可自定义
                  .network(BroadcastNetwork(this))//记录网络变化
//                  .addInterceptor(LoginInterceptor())//可自定义拦截器，比如参数签名、Token/Cookie失效时登录重试、失败后重试、Log打印、重定向等等
                  .addInterceptor(LoggerInterceptor(this.getString(R.string.app_name), BuildConfig.DEBUG))//打印日志功能
                  .converter(MyConverter(this))//可自定义转换器，将接口返回的参数转换为统一格式，与callback一起处理接口结果，ps：全局的设置也可以在这里进行，比如接口code为xxx时跳转登录界面
                  .setHeader("Content-Type","application/json")
                  .build())

          val formatStrategy = PrettyFormatStrategy.newBuilder().tag("lyy").build()
          Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))

          Gloading.debug(Debug)
          Gloading.initDefault(GlobalAdapter())
     }

//     在非页面生命周期内(比如BroadcastReceiver)获取Activity和上下文用
     fun getCurrentActivity(): Activity? {
          return mCurrentActivity
     }

     fun setCurrentActivity(mCurrentActivity: Activity?) {
          this.mCurrentActivity = mCurrentActivity
     }
}