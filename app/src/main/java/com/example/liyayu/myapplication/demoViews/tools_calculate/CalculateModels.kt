package com.example.liyayu.myapplication.demoViews.tools_calculate

import java.io.Serializable

/**
 * Created by liyayu on 2019/5/23.
 */

class PlanSettingModel :Serializable{
    var plans = mutableListOf<PlanModel>()
}

class PlanModel:Serializable{
    var planName = "" //方案名
    var calOut = "" //外方管横截面
    var calIn = "" //内方管横截面
    var calRound = "" //竖圆管直径
    var calTubeGap = "" //横方管间隔
    var calRoundGap = "" //竖圆管间隔
    var calPunchPin = "" //冲头直径
    var calPunchPercent = "" //冲模占空比
}
