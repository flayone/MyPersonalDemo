package com.example.liyayu.myapplication.baseFramework

import android.content.Context
import android.os.Handler
import android.os.Message
import java.lang.ref.WeakReference

/**
 * Created by liyayu on 2018/5/11.
 * 防止内存泄漏的handler
 */
open class BaseHandler (var context : Context?): Handler() {
    //持有弱引用HandlerActivity,GC回收时会被回收掉.
    private val mContext: WeakReference<Context> = WeakReference<Context>(context)

    override fun handleMessage(msg: Message?) {
        super.handleMessage(msg)
        if (mContext.get() !=null){
           baseHandleMessage(msg!!)
        }
    }

    open fun baseHandleMessage(msg: Message) {}
}