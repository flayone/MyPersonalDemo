package com.example.liyayu.myapplication.dialog

import android.app.Dialog
import android.content.Context

/**
 * Created by liyayu on 2018/12/20.
 * 对话框
 */

abstract class BaseKtDialog : Dialog {
    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, themeStyle: Int) : super(context, themeStyle) {
        initView()
    }

    abstract fun initView()
}
