package com.example.liyayu.myapplication.baseFramework

import android.app.Activity
import android.os.Handler
import android.os.Message
import java.lang.ref.WeakReference

/**
 * Created by liyayu on 2018/5/11.
 * 防止内存泄漏的handler
 */
open class BaseHandler (var activity : Activity?): Handler() {
    //持有弱引用HandlerActivity,GC回收时会被回收掉.
    private val mActivity: WeakReference<Activity> = WeakReference<Activity>(activity)

    override fun handleMessage(msg: Message?) {
        super.handleMessage(msg)
        if (mActivity.get() !=null){
           baseHandleMessage(msg!!)
        }
    }

    open fun baseHandleMessage(msg: Message) {}
}