package com.example.liyayu.myapplication.http

import android.os.Parcel
import android.os.Parcelable
import com.alibaba.fastjson.annotation.JSONField

/**
 * Created by liyayu on 2018/8/28.
 * 接口请求接收的返回参数结构实体
 */
class HttpEntity() : Parcelable {

    @JSONField(name = "code")
    var mCode: String? = null

    @JSONField(name = "message")
    var mMessage: String? = null

    @JSONField(name = "content")
    var mData: String? = null

    constructor(parcel: Parcel) : this() {
        mCode = parcel.readString()
        mMessage = parcel.readString()
        mData = parcel.readString()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(mCode)
        dest.writeString(mMessage)
        dest.writeString(mData)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HttpEntity> {
        override fun createFromParcel(parcel: Parcel): HttpEntity {
            return HttpEntity(parcel)
        }

        override fun newArray(size: Int): Array<HttpEntity?> {
            return arrayOfNulls(size)
        }
    }
}