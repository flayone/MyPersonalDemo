package com.example.liyayu.myapplication.baseFramework

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.os.Looper
import android.os.PersistableBundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.liyayu.myapplication.BuildConfig
import com.example.liyayu.myapplication.R
import com.example.liyayu.myapplication.util.DisplayUtils
import com.example.liyayu.myapplication.util.InputUtils
import com.example.liyayu.myapplication.util.MyLogger
import com.example.liyayu.myapplication.util.StatusBarUtil
import com.yanzhenjie.kalle.Kalle
import java.lang.Exception


/**
 * Created by liyayu on 2018/3/21.
 * kt基类activity
 */
@SuppressLint("Registered")
open class BaseKotlinActivity : AppCompatActivity(), MyLogger {
    var toolbar: android.support.v7.widget.Toolbar? = null
    private lateinit var mApp: BaseApplication
    //    private var mAppTwo :BaseApplication = BaseApplication()//此种方式相当于新建一个Application，非单例用途
    private var mAppBackUp: BaseApplication? = null//备用
    private var mAppContext: Context? = null//Application生命周期的上下文
    var debug: Boolean = BuildConfig.DEBUG
    var isAlive: Boolean = true
    var stateSaved = false //标记Activity的状态是否已经保存

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mApp = this.application as BaseApplication
        mAppBackUp = BaseApplication.instance
        mAppContext = if (mApp.applicationContext != null) {
            mApp.applicationContext
        } else {
            mAppBackUp!!.applicationContext
        }
        DisplayUtils().initScreen(this)
        isAlive = true
        stateSaved = false
        if (debug) {
            testInject()
        }
        d("onCreate()= ${javaClass.name} ,localClassName = $localClassName ")
    }

    override fun onResume() {
        d("onResume()= $localClassName ")
        isAlive = true
        stateSaved = false

        mApp.setCurrentActivity(this)
        super.onResume()
    }

    override fun onPause() {
        d("onPause()= $localClassName ")
        clearReferences()
        isAlive = false
        super.onPause()
    }

    override fun onDestroy() {
        d("onDestroy()= $localClassName ")
        clearReferences()
        Kalle.cancel(this)
        isAlive = false
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        stateSaved = true
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        stateSaved = false
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
            supportActionBar!!.run {
                setHomeButtonEnabled(true)
                setDisplayHomeAsUpEnabled(true)
            }
        } catch (e: Exception) {
            d(e.toString())
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
        showFragment(resId, fragment, 0)
    }

    fun replaceFragment(resId: Int, fragment: Fragment) {
        showFragment(resId, fragment, 1)
    }

    //isNewPage 0 新增 1 替换
    private fun showFragment(resId: Int, fragment: Fragment, isNewPage: Int) {
        val tag = fragment.tag
        InputUtils.hideInput(this)
        val ft = supportFragmentManager.beginTransaction()
        if (isActivityShowing() && supportFragmentManager.findFragmentByTag(tag) == null) {
            when (isNewPage) {
                0 -> ft.addToBackStack(tag).add(resId, fragment, tag).commitAllowingStateLoss()

                1 -> ft.replace(resId, fragment, tag).commitAllowingStateLoss()
            }
        }
    }

    //Activity正在展示在页面上
    private fun isActivityShowing(): Boolean = !stateSaved && !isFinishing && !isDestroyed

    open fun initView() {
    }

    fun startAct(activity: Activity) {
        startAct(activity::class.java)
    }

    fun startAct(cls: Class<*>) {
        startActivity(Intent(this, cls))
    }

    @JvmOverloads
    fun setStatusColor(res: Int, isTextBlack: Boolean = false) {
        StatusBarUtil.setStatusBarMode(this, isTextBlack, res)
    }

    private fun testInject() {
        Looper.myQueue().addIdleHandler {
            val annotation = this@BaseKotlinActivity::class.java.getAnnotation(TestInject::class.java)
            if (annotation != null) {
                startActivity(Intent(this@BaseKotlinActivity, annotation.value.java))
            }
            false
        }

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