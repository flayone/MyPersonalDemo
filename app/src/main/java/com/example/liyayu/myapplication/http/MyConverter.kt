package com.example.liyayu.myapplication.http

import android.content.Context
import com.alibaba.fastjson.JSON
import com.example.liyayu.myapplication.R
import com.example.liyayu.myapplication.util.LogUtil
import com.yanzhenjie.kalle.Response
import com.yanzhenjie.kalle.simple.Converter
import com.yanzhenjie.kalle.simple.SimpleResponse
import java.lang.reflect.Type


/**
 * Created by liyayu on 2018/8/28.
 * 网络转换器是把服务器的响应转换为本地预期的结果的一个工具，在Kalle中Converter可以被配置为全局的，也可以为单个Request指定。
 */
class MyConverter(private val mContext: Context) : Converter {
    // fail code
    val B_FAILURE = "1000"
    //success code
    val B_OK = "0000"
    // unuseful token
    val B_TOKEN_UNUSEFUL = "0001"
    // update password
    val B_PASSWORD_CHANGE = "0002"
    // finish work
    val B_FINISH_GRADUA = "0003"
    // start grauda
    val B_START_GRADUA = "0004"
    // 询价特殊返回code
    val B_SET_IN_FAILXP = "0005"
    // show in failed and need to cinfig
    val B_SET_IN_FAILURED = "0007"
    // old version local
    val B_NEW_VERSION = "0008"

    override fun <S : Any, F : Any> convert(succeed: Type, failed: Type, response: Response, fromCache: Boolean): SimpleResponse<S, F> {

        var succeedData: S? = null // The data when the business successful.
        var failedData: F? = null // The data when the business failed.

        val code = response.code()
        val serverJson = response.body().string()
        LogUtil.d("Server Data: " + serverJson)
        when {
            code in 200..299 -> { // Http is successful.
                var httpEntity: HttpEntity
                try {
                    httpEntity = JSON.parseObject(serverJson, HttpEntity::class.java)
                } catch (e: Exception) {
                    httpEntity = HttpEntity()
                    httpEntity.mCode = B_FAILURE
                    httpEntity.mMessage = mContext.getString(R.string.http_server_data_format_error)
                }

                LogUtil.d("httpEntity.mCode = ${httpEntity.mCode}")
                when (httpEntity.mCode) {
                // 成功，返回成功数据
                    B_OK -> try {
                        succeedData = JSON.parseObject(httpEntity.mCode, succeed)
                    } catch (e: Exception) {
                        failedData = mContext.getString(R.string.http_server_data_format_error) as F
                    }
                    B_FAILURE -> failedData = httpEntity.mMessage as F
                    B_TOKEN_UNUSEFUL, B_NEW_VERSION -> {
                        failedData = httpEntity.mMessage as F
//                    ToastUtil.showToast(mContext, failedData as String)
                        //TOKEN失效或需要强制更新 跳转登录界面 ReLogin
//                    if (mContext is BActivity) {
//                        (mContext as BActivity).logout()
//                    } else {
//                        val intent = Intent()
//                        intent.setClass(mContext, LoginActivity::class.java!!)
//                        mContext.startActivity(intent)
//                    }
                    }
                    B_PASSWORD_CHANGE -> {
                        failedData = httpEntity.mMessage as F
                        //展示修改密码页面
                    }
                    B_FINISH_GRADUA -> {
                        failedData = httpEntity.mMessage as F
                        //关闭所有页面 并跳转结业页面
                    }
                    B_START_GRADUA -> {
                        failedData = httpEntity.mMessage as F
                        //关闭所有页面 并跳转开业页面
                    }
                    B_SET_IN_FAILXP, B_SET_IN_FAILURED -> {
//                        统一在页面逻辑处 做相应处理的
                        failedData = httpEntity.mMessage as F
                    }
                    else -> failedData = httpEntity.mMessage as F
                }
            }
            code in 400..499 -> failedData = mContext.getString(R.string.http_unknow_error) as F
            code >= 500 -> failedData = mContext.getString(R.string.http_server_error) as F
        }

        return SimpleResponse.newBuilder<S, F>()
                .code(response.code())
                .headers(response.headers())
                .fromCache(fromCache)
                .succeed(succeedData)
                .failed(failedData)
                .build();
    }
}