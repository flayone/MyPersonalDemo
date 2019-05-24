package com.example.liyayu.myapplication.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import com.example.liyayu.myapplication.baseFramework.BaseApplication
import com.example.liyayu.myapplication.baseFramework.BaseKotlinActivity
import com.example.liyayu.myapplication.util.BigDecimalUtils
import org.jetbrains.anko.windowManager

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

fun hideDialogKeyboard(dialog: Dialog?, context: Context) {
    try {
        var b = dialog?.window!!.decorView.windowToken
        if (b == null) {
            val a = context as BaseKotlinActivity
            b = a.currentFocus.windowToken
        }
        //隐藏软键盘
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(b, InputMethodManager.HIDE_NOT_ALWAYS)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**
 * layoutId 布局id  widthPercent，heightPercent 分别是宽高相对屏幕的百分比，默认0.5代表各一半
 */
open class BaseKtLayoutDialog @JvmOverloads constructor(context: Context, layoutId: Int, private val widthPercent: Float = 0.5f, private val heightPercent: Float = 0.5f, private val isBgTrans: Boolean = false, themeStyle: Int = 0) : Dialog(context, themeStyle) {
    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)//设置Dialog没有标题。需在setContentView之前设置，在之后设置会报错
        this.setContentView(layoutId)
    }

    //    override fun setContentView(layoutResID: Int) = super.setContentView(layoutId)
    override fun show() {
        super.show()
        /**
         * 设置具体高宽，要设置在show的后面
         */
        val layoutParams = window.attributes
        val screenSize = Point()
        BaseApplication.instance.windowManager.defaultDisplay.getSize(screenSize)
        val screenWidth = screenSize.x
        val screenHeight = screenSize.y
        layoutParams.gravity = Gravity.CENTER
        layoutParams.width = when (widthPercent.toInt()) {
            ViewGroup.LayoutParams.WRAP_CONTENT -> ViewGroup.LayoutParams.WRAP_CONTENT
            ViewGroup.LayoutParams.MATCH_PARENT -> ViewGroup.LayoutParams.MATCH_PARENT
            else -> BigDecimalUtils.multiply(screenWidth.toString(), widthPercent.toString()).toDouble().toInt()
        }
        layoutParams.height = when (heightPercent.toInt()) {
            ViewGroup.LayoutParams.WRAP_CONTENT -> ViewGroup.LayoutParams.WRAP_CONTENT
            ViewGroup.LayoutParams.MATCH_PARENT -> ViewGroup.LayoutParams.MATCH_PARENT
            else -> BigDecimalUtils.multiply(screenHeight.toString(), heightPercent.toString()).toDouble().toInt()

        }
        window.attributes = layoutParams

        if (isBgTrans) {
//如果你是圆角之类的话，这句设置背景透明要加上。
//否则有个白底在那儿，你的dialog也是白色的话是看不到圆角的
            window.setBackgroundDrawableResource(android.R.color.transparent)
        }
        hideDialogKeyboard(this, context)
    }
}

fun dialogRecycleLayoutManger(context: Context): LinearLayoutManager {
    val layoutManager = object : LinearLayoutManager(context) {
        override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams =
                RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
    }
    layoutManager.orientation = LinearLayoutManager.VERTICAL
    return layoutManager
}