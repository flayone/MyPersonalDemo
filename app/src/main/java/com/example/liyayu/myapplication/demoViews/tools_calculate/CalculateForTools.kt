package com.example.liyayu.myapplication.demoViews.tools_calculate

import android.os.Bundle
import com.example.liyayu.myapplication.R
import com.example.liyayu.myapplication.baseFramework.BaseKotlinActivity
import com.example.liyayu.myapplication.util.LOCAL_Data
import com.example.liyayu.myapplication.util.SETTING_PLANS
import com.example.liyayu.myapplication.util.saveObject

/**
 * Created by liyayu on 2019/5/22.
 * 门窗计算器
 */
class CalculateForTools :BaseKotlinActivity(){
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
        saveObject(this,LOCAL_Data, SETTING_PLANS,defaultSettings)
    }
}