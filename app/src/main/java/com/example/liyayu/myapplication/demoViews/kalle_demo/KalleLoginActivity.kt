package com.example.liyayu.myapplication.demoViews.kalle_demo

import android.os.Bundle
import com.alibaba.fastjson.JSON
import com.example.liyayu.myapplication.R
import com.example.liyayu.myapplication.baseFramework.BaseKotlinActivity
import com.example.liyayu.myapplication.http.HomesLoginModel
import com.example.liyayu.myapplication.http.HomesLoginResponseModel
import com.example.liyayu.myapplication.util.ToastUtil
import com.yanzhenjie.kalle.JsonBody
import com.yanzhenjie.kalle.Kalle
import com.yanzhenjie.kalle.simple.SimpleCallback
import com.yanzhenjie.kalle.simple.SimpleResponse
import kotlinx.android.synthetic.main.activity_kalle_login.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by liyayu on 2018/10/19.
 */
class KalleLoginActivity : BaseKotlinActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kalle_login)
        if (debug) {
            code_ed.setText("v1101")
            psw_ed.setText("123456")
        }
        login_btn.onClick {
            val req = HomesLoginModel()
            req.passwd = psw_ed.text.toString()
            req.userAccount = code_ed.text.toString()

            val obj = JsonBody(JSON.toJSONString(req))
            obj.onProgress { origin, progress -> d("origin = $origin  progress = $progress") }
            Kalle.post("http://home.chexiangpre.com/pad/security/login").tag(this).body(obj)
                    .perform(object : SimpleCallback<HomesLoginResponseModel>() {
                        override fun onResponse(response: SimpleResponse<HomesLoginResponseModel, String>) {
                            if (response.isSucceed) {
                                val data = response.succeed()
                                ToastUtil.showToast(this@KalleLoginActivity, "empId = ${data.empId} accessToken=${data.accessToken}")

                            } else {
                                ToastUtil.showToast(this@KalleLoginActivity, response.failed())
                            }
                        }

                    })

        }
        register_btn.onClick {
            Kalle.get("http://home.chexiangpre.com/pad/order/queryReserveOrderCount")
//                    .setHeader()
                    .perform(object : SimpleCallback<HomesLoginResponseModel>() {
                        override fun onResponse(response: SimpleResponse<HomesLoginResponseModel, String>) {
                            if (response.isSucceed) {
                                val data = response.succeed()
//                                ToastUtil.showToast(this@KalleLoginActivity, "empId = ${data.empId} accessToken=${data.accessToken}")

                            } else {
                                ToastUtil.showToast(this@KalleLoginActivity, response.failed())
                            }
                        }

                    })
        }
    }

}