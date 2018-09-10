package com.example.liyayu.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.liyayu.myapplication.baseFramework.BaseKotlinActivity
import com.example.liyayu.myapplication.util.LogUtil
import kotlinx.android.synthetic.main.activity_test_err.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*

/**
 * Created by liyayu on 2018/9/7.
 */
class TestErr : BaseKotlinActivity() {
    private lateinit var mIntent: Intent
    private var mStringList = ArrayList<String>()
    private lateinit var mStringBuilder: StringBuilder
    private lateinit var mString: String
    private var REQUEST_CODE_TEST = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_err)
        val size = 504 * 5
        mStringBuilder = StringBuilder(size)
        for (i in 0 until size) {
            mStringBuilder.append("a")
        }
        //这个mString占1024bytes
        mString = mStringBuilder.toString()
        btn_go.onClick {
            openActivity()
        }
    }

    private fun openActivity() {
        try {
            mIntent = Intent(this@TestErr, TestTwoActivity::class.java)
            //这里要注意的，只有每次都add一个新的字符串，mStringList的大小才会线性变大，否则，指向的是同一个地址
            //但是，即使指向同一个地址，在put的时候也是会出错的，原因，就是因为put要放入每一个值吧。
            mStringList.add(mStringBuilder.toString())
            mIntent.putStringArrayListExtra("string_list", mStringList)
            Log.d("test", "put size:" + mStringList.size)
            //            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //                startActivityForResult(mIntent, REQUEST_CODE_TEST,ActivityOptions.makeSceneTransitionAnimation(TestOneActivity.this).toBundle());
            //            }else {
            startActivityForResult(mIntent, REQUEST_CODE_TEST)
            //            }
        } catch (e: Exception) {
            e.printStackTrace()
            LogUtil.d("Exception.mStringList.size()=====" + mStringList.size + e.toString())
        }
    }

    //在这里在调用打开，就循环了
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        LogUtil.d("onActivityResult.  isAlive =" + isAlive)
        if (REQUEST_CODE_TEST == requestCode && Activity.RESULT_OK == resultCode) {
            openActivity()
            LogUtil.d("onActivityResult--> openActivity")
        }
    }
}