package com.example.liyayu.myapplication

import android.annotation.SuppressLint
import android.content.ComponentName
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.example.liyayu.myapplication.util.InputUtils
import com.example.liyayu.myapplication.util.LogUtils
import java.lang.Exception

/**
 * Created by liyayu on 2018/3/21.
 * kt基类activity
 */
@SuppressLint("Registered")
open class BaseKotlinActivity : AppCompatActivity() {
    var toolbar: Toolbar? = null
    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        try {
            //设置默认的toolbar
            toolbar = findViewById(R.id.tb_toolbar)
            setSupportActionBar(toolbar)
            supportActionBar!!.setHomeButtonEnabled(true)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        } catch (e: Exception) {
            LogUtils.d(e.toString())
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

    fun startAct( cls : Class<*> ){
        intent.component = ComponentName(this,cls)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}