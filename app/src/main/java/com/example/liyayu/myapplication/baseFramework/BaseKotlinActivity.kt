package com.example.liyayu.myapplication.baseFramework

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.liyayu.myapplication.R
import com.example.liyayu.myapplication.util.InputUtils
import com.example.liyayu.myapplication.util.LogUtil
import java.lang.Exception


/**
 * Created by liyayu on 2018/3/21.
 * kt基类activity
 */
@SuppressLint("Registered")
open class BaseKotlinActivity : AppCompatActivity() {
    var toolbar: Toolbar? = null
    private lateinit var mApp: BaseApplication
    var debug: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mApp = this.application as BaseApplication
        debug = mApp.Debug
    }

    override fun onResume() {
        super.onResume()
        mApp.setCurrentActivity(this)
    }

    override fun onPause() {
        clearReferences()
        super.onPause()
    }

    override fun onDestroy() {
        clearReferences()
        super.onDestroy()
    }

    private fun clearReferences() {
        val currActivity = mApp.getCurrentActivity()
        if (this == currActivity)
            mApp.setCurrentActivity(null)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        try {
            //设置默认的toolbar
            toolbar = findViewById(R.id.tb_toolbar)
            setSupportActionBar(toolbar)
            supportActionBar!!.setHomeButtonEnabled(true)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        } catch (e: Exception) {
            LogUtil.d(e.toString())
        }
        initView()
    }

    /**
     * 在当前activity的fragment栈的栈顶压入新的fragment
     *
     * @param resId
     * @param fragment
     */
    fun pushFragment(resId: Int, fragment: Fragment) {
        val tag = fragment.javaClass.simpleName
        InputUtils.hideInput(this)
        val ft = supportFragmentManager.beginTransaction()
        ft.addToBackStack(tag).add(resId, fragment, tag).commitAllowingStateLoss()
    }

    open fun initView() {
    }

    fun startAct(cls: Class<*>) {
        startActivity(Intent(this, cls))
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v!!.windowToken)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     * @param v
     * @param event
     * @return
     */
    private fun isShouldHideKeyboard(v: View?, event: MotionEvent): Boolean {
        if (v != null && v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.getHeight()
            val right = left + v.getWidth()
            return !(event.x > left && event.x < right
                    && event.y > top && event.y < bottom)
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private fun hideKeyboard(token: IBinder?) {
        if (token != null) {
            val im = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }
}