package com.example.liyayu.myapplication.jpush

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.util.Log
import cn.jpush.android.api.JPushInterface
import cn.jpush.android.api.TagAliasCallback
import com.example.liyayu.myapplication.BuildConfig
import com.example.liyayu.myapplication.util.isConnected
import java.util.*

/**
 * Created by liyayu on 2018/5/22.
 */
class PushSettingKt {
    private val TAG = "JPush"
    private val MSG_SET_ALIAS = 1001
    private val MSG_SET_TAGS = 1002
    private var mContext: Context? = null
    fun setTag(context: Context, s: String) {
        mContext = context
        val tagSet = LinkedHashSet<String>()
        tagSet.add(s)
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, tagSet))
    }

    private val mHandler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: android.os.Message) {
            super.handleMessage(msg)
            when (msg.what) {
                MSG_SET_ALIAS -> Log.d(TAG, "Set alias in handler.")

                MSG_SET_TAGS -> {
                    Log.d(TAG, "Set tags in handler.")
                    JPushInterface.setAliasAndTags(mContext!!, null, msg.obj as Set<String>, mTagsCallback)
                }

                else -> Log.i(TAG, "Unhandled msg - " + msg.what)
            }//                    JPushInterface.setAliasAndTags(mContext, (String) msg.obj, null, mAliasCallback);
        }
    }
    private val mTagsCallback = TagAliasCallback { code, alias, tags ->
        val logs: String
        when (code) {
            0 -> {
                logs = "Set tag and alias success"
                if (BuildConfig.DEBUG)
                    Log.i(TAG, logs)
            }

            6002 -> {
                logs = "Failed to set alias and tags due to timeout. Try again after 60s."
                Log.i(TAG, logs)
                if (isConnected(mContext!!)) {
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_TAGS, tags), (1000 * 60).toLong())
                } else {
                    Log.i(TAG, "No network")
                }
            }

            else -> {
                logs = "Failed with errorCode = " + code
                Log.e(TAG, logs)
            }
        }
    }

}