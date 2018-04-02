package com.example.liyayu.myapplication

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.example.liyayu.myapplication.util.InputUtils
import org.jetbrains.anko.find

/**
 * Created by liyayu on 2018/3/21.
 */
open class BaseKotlinActivity : AppCompatActivity() {
    var toolbar: Toolbar? = null
    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        //设置默认的toolbar
        toolbar = find(id = R.id.tb_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}