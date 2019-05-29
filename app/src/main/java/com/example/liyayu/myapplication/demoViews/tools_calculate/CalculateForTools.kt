package com.example.liyayu.myapplication.demoViews.tools_calculate

import android.os.Bundle
import com.example.liyayu.myapplication.R
import com.example.liyayu.myapplication.baseFramework.BaseKotlinActivity
import com.example.liyayu.myapplication.util.*
import kotlinx.android.synthetic.main.activity_calculate_tools.*

/**
 * Created by liyayu on 2019/5/22.
 * 门窗计算器
 */
class CalculateForTools : BaseKotlinActivity() {

    private var nowPlan = PlanModel()
    var result = resultModel()
    private val verticalDisCount = "1.5"//竖圆管差值，用来计算竖圆管高度


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate_tools)

        val defaultSettings = PlanSettingModel()
        val defaultPlan = PlanModel()
        defaultPlan.planName = "默认"
        defaultPlan.calOut = "2.5"
        defaultPlan.calIn = "2.5"
        defaultPlan.calRound = "2.2"
        defaultPlan.calRoundGap = "10"
        defaultPlan.calTubeGap = "35"
        defaultPlan.calPunchPin = "2.23"
        defaultPlan.calPunchPercent = "3.6"
        defaultSettings.plans.add(defaultPlan)
        saveObject(this, LOCAL_Data, SETTING_PLANS, defaultSettings)
        iv_ct_setting.setOnClickListener {
            CalculateSettingDialog(this@CalculateForTools, object : BaseClickListener {
                override fun onClick() {
                    initSetting()
                }
            }).show()
        }
        btn_ct_calculate.setOnClickListener {

            val h = et_ct_height.text.toString()
            val w = et_ct_width.text.toString()
            if (h.isEmpty()) {
                showToast("请输入高度")
                return@setOnClickListener
            }
            if (w.isEmpty()) {
                showToast("请输入宽度")
                return@setOnClickListener
            }

            result.horizontalWidth = BigDecimalUtils.subtract(w, BigDecimalUtils.multiply(nowPlan.calOut, "2"))
            result.horizontalCount = BigDecimalUtils.add(BigDecimalUtils.div(BigDecimalUtils.add(h, nowPlan.calIn), BigDecimalUtils.add(nowPlan.calTubeGap,nowPlan.calIn), 2).toInt().toString(), "1")
            val totalHorizontalGap = BigDecimalUtils.subtract(BigDecimalUtils.subtract(h, BigDecimalUtils.multiply(nowPlan.calOut, "2")), BigDecimalUtils.multiply(result.horizontalCount, nowPlan.calIn))
            result.horizontalGap = BigDecimalUtils.div(totalHorizontalGap, BigDecimalUtils.subtract(result.horizontalCount, "1"), 2)

            result.verticalLength = BigDecimalUtils.subtract(h, verticalDisCount)
            result.verticalCount = BigDecimalUtils.div(BigDecimalUtils.add(w, nowPlan.calRound), BigDecimalUtils.add(nowPlan.calRound, nowPlan.calRoundGap), 2)
            val totalVerticalGap = BigDecimalUtils.subtract(result.horizontalWidth, BigDecimalUtils.multiply(nowPlan.calRound, result.verticalCount))
            result.verticalGap = BigDecimalUtils.div(totalVerticalGap, BigDecimalUtils.subtract(result.verticalCount, "1"), 2)


            tv_ct_01.text = "横方管宽度 :${result.horizontalWidth}  \n" +
                    " 横方管数量:${result.horizontalCount} \n" +
                    " 横方管间隔:${result.horizontalGap} \n" +
                    "竖圆管长度 :${result.verticalLength}  \n" +
                    " 竖圆管数量:${result.verticalCount} \n "
            " 竖圆管间隔:${result.verticalGap} \n "


        }

    }

    fun initSetting() {
        val data = getObject(this, LOCAL_Data, SETTING_PLANS) as PlanSettingModel
        for (i in 0 until data.plans.size) {
            if (data.plans[i].isSelected) {
                nowPlan = data.plans[i]
            }
        }

    }

}