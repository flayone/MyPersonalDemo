package com.example.liyayu.myapplication.baseFramework

import android.annotation.SuppressLint
import android.content.ComponentName
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.example.liyayu.myapplication.BuildConfig
import com.example.liyayu.myapplication.R
import com.example.liyayu.myapplication.demoViews.hotfixRobustDemo.DownloadPatchManger
import com.example.liyayu.myapplication.util.InputUtils
import com.example.liyayu.myapplication.util.LogUtil
import com.example.liyayu.myapplication.util.ToastUtil
import com.example.liyayu.myapplication.util.permission.PermissionUtil
import java.lang.Exception

/**
 * Created by liyayu on 2018/3/21.
 * kt基类activity
 */
@SuppressLint("Registered")
open class BaseKotlinActivity : AppCompatActivity() {
    var toolbar: Toolbar? = null
    var app : BaseApplication = BaseApplication.instance
    var debug = BuildConfig.DEBUG

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

//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        when (requestCode) {
//            REQUEST_CODE_SDCARD_READ -> DownloadPatchManger.getInstance(this).handlePermissionResult()
//            else -> {
//            }
//        }
//    }

    protected fun getPatch() {
        //权限校验
        PermissionUtil.doTaskWithPermissions(this@BaseKotlinActivity
                , "为保证app功能正常，需要存储权限"
                , arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                , object : PermissionUtil.Callback() {
            override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>?) {
                (0 until perms!!.size)
                        .map { perms[it] }
                        .forEach { LogUtil.d("被拒绝的权限:" + it) }
                ToastUtil.showToast(this@BaseKotlinActivity, "应用存储权限获取被拒绝")
            }

            override fun onAfterAllPermissionGranted(requestCode: Int, perms: MutableList<String>?) {
                LogUtil.d("go" +
                        "DownloadPatchManger:")
                DownloadPatchManger.getInstance(this@BaseKotlinActivity, "http://s1.cximg.com/downloads/cxj/apk/cxj-homes-prd-v1.3.2-20180420.apk").doDownloadThread()
            }
        })
    }
}