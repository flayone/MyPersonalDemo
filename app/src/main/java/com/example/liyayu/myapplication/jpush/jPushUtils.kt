package com.example.liyayu.myapplication.jpush

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import com.example.liyayu.myapplication.R
import com.example.liyayu.myapplication.util.ToastUtil
import java.util.*
import java.util.regex.Pattern

/**
 * Created by liyayu on 2018/6/13.
 * 极光推送相关设置
 */
/**
 * 获取输入的tags
 */

fun setJpushAlias(context : Context,string: String){
    val tagAliasBean  = TagAliasBean()
    tagAliasBean.action = TagAliasOperatorHelper.ACTION_SET
    TagAliasOperatorHelper.sequence++
    tagAliasBean.alias = string
    tagAliasBean.isAliasAction = true
    TagAliasOperatorHelper.getInstance().handleAction(context.applicationContext, TagAliasOperatorHelper.sequence, tagAliasBean)
}

fun setJpushTag(context : Context,string: String){
    val tagAliasBean  = TagAliasBean()
    tagAliasBean.action = TagAliasOperatorHelper.ACTION_SET
    TagAliasOperatorHelper.sequence++
    tagAliasBean.tags = getInPutTags(string,context)
    tagAliasBean.isAliasAction = false
    TagAliasOperatorHelper.getInstance().handleAction(context.applicationContext, TagAliasOperatorHelper.sequence, tagAliasBean)
}

private fun getInPutTags(string: String,context : Context): Set<String>? {
    // 检查 tag 的有效性
    if (TextUtils.isEmpty(string)) {
        ToastUtil.showToast(context, R.string.error_tag_empty)
        return null
    }
    // ","隔开的多个 转换成 Set
    val sArray = string.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    val tagSet = LinkedHashSet<String>()
    for (sTagItme in sArray) {
        if (!isValidTagAndAlias(sTagItme)) {
            ToastUtil.showToast(context, R.string.error_tag_gs_empty)
            return null
        }
        tagSet.add(sTagItme)
    }
    if (tagSet.isEmpty()) {
        ToastUtil.showToast(context, R.string.error_tag_empty)
        return null
    }
    return tagSet
}

/**
 * 只能以 “+” 或者 数字开头；后面的内容只能包含 “-” 和 数字。
 */
private val MOBILE_NUMBER_CHARS = "^[+0-9][-0-9]{1,}$"

fun isValidMobileNumber(s: String): Boolean {
    if (TextUtils.isEmpty(s)) return true
    val p = Pattern.compile(MOBILE_NUMBER_CHARS)
    val m = p.matcher(s)
    return m.matches()
}

// 校验Tag Alias 只能是数字,英文字母和中文
fun isValidTagAndAlias(s: String): Boolean {
    val p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_!@#$&*+=.|]+$")
    val m = p.matcher(s)
    return m.matches()
}

val KEY_APP_KEY = "JPUSH_APPKEY"

// 取得AppKey
fun getAppKey(context: Context): String? {
    var metaData: Bundle? = null
    var appKey: String? = null
    try {
        val ai = context.packageManager.getApplicationInfo(
                context.packageName, PackageManager.GET_META_DATA)
        if (null != ai)
            metaData = ai.metaData
        if (null != metaData) {
            appKey = metaData.getString(KEY_APP_KEY)
            if (null == appKey || appKey.length != 24) {
                appKey = null
            }
        }
    } catch (e: PackageManager.NameNotFoundException) {

    }

    return appKey
}