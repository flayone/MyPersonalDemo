package com.example.liyayu.myapplication.http

import java.io.Serializable

/**
 * Created by liyayu on 2018/8/28.
 * 接口请求接收的返回参数结构实体
 */
//
//@Parcelize
//class HttpEntity(@JSONField(name = "status")
//                 var mStatus: String? = null,
//                 @JSONField(name = "message")
//                 var mMessage: String? = null,
//                 @JSONField(name = "content")
//                 var mContent: String? = null) : Parcelable

class HttpEntity:Serializable{
    var status= ""
    var message= ""
    var content : Any? = null
}