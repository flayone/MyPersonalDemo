package com.example.liyayu.myapplication.demoViews.tools_calculate

import android.content.Context
import android.widget.ArrayAdapter
import com.example.liyayu.myapplication.R
import com.example.liyayu.myapplication.dialog.BaseKtLayoutDialog
import com.example.liyayu.myapplication.util.*
import kotlinx.android.synthetic.main.dialog_calculate_setting.*
import kotlinx.android.synthetic.main.dialog_calculate_setting_modify.*


/**
 * Created by liyayu on 2019/5/23.
 */
class CalculateSettingDialog(mContext: Context) : BaseKtLayoutDialog(mContext, R.layout.dialog_calculate_setting, 0.8f, -2f) {
    init {
        var selectPos = 0
        val data = getObject(mContext, LOCAL_Data, SETTING_PLANS) as PlanSettingModel
        val list = data.plans
        val payType = arrayOfNulls<String>(list.size)
        for (i in 0 until list.size) {
            payType[i] = list[i].planName
        }
        val adapter = ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, payType)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_dcs_name.adapter = adapter
        sp_dcs_name.setOnItemClickListener { adapterView, view, i, l ->
            selectPos = i
            val model = list[i]
            tv_dcs_out.text = "外方管横截面(cm)：${model.calOut}"
            tv_dcs_in.text = "内方管横截面(cm)：${model.calIn}"
            tv_dcs_round.text = "竖圆管直径(cm)：${model.calRound}"
            tv_dcs_tube_gap.text = "横方管间隔(cm)：${model.calTubeGap}"
            tv_dcs_round_gap.text = "竖圆管间隔(cm)：${model.calRoundGap}"
            tv_dcs_punch_pin.text = "冲头直径(cm)：${model.calPunchPin}"
            tv_dcs_punch_percent.text = "冲模占空比(cm)：${model.calPunchPercent}"
        }
        btn_dcs_del.setOnClickListener {
            list.removeAt(selectPos)

        }
        btn_dcs_modify.setOnClickListener {
            val dialog = UpdateSettingDialog(mContext, selectPos)
            dialog.show()
        }
        btn_dcs_add.setOnClickListener {
            val dialog = UpdateSettingDialog(mContext)
            dialog.show()
        }
    }
}

class UpdateSettingDialog(mContext: Context, position: Int = -1) : BaseKtLayoutDialog(mContext, R.layout.dialog_calculate_setting_modify, 0.8f, -2f) {
    init {

        val data = getObject(mContext, LOCAL_Data, SETTING_PLANS) as PlanSettingModel
        val list = data.plans

        val isAdd: Boolean
        if (position < 0) {
            tv_dcsm_title.text = "新增方案"
            et_dcsm_name.isEnabled = true
            isAdd = true
        } else {

            val bean = list[position]
            tv_dcsm_title.text = "修改方案"
            et_dcsm_name.isEnabled = false

            et_dcsm_name.setText(bean.planName)
            et_dcsm_out.setText(bean.calOut)
            et_dcsm_in.setText(bean.calIn)
            et_dcsm_round.setText(bean.calRound)
            et_dcsm_tube_gap.setText(bean.calTubeGap)
            et_dcsm_round_gap.setText(bean.calRoundGap)
            et_dcsm_punch_pin.setText(bean.calPunchPin)
            et_dcsm_punch_percent.setText(bean.calPunchPercent)
            isAdd = false

        }
        btn_dcsm_ensure.setOnClickListener {
            val isAlready = et_dcsm_name.text.isNotEmpty() && et_dcsm_out.text.isNotEmpty() && et_dcsm_in.text.isNotEmpty()
                    && et_dcsm_round.text.isNotEmpty() && et_dcsm_tube_gap.text.isNotEmpty() && et_dcsm_round_gap.text.isNotEmpty()
                    && et_dcsm_punch_pin.text.isNotEmpty() && et_dcsm_punch_percent.text.isNotEmpty()
            if (!isAlready) {
                showToast("请填写完相应数据")
                return@setOnClickListener
            }

            val bean = PlanModel()
            bean.planName = et_dcsm_name.text.toString()
            bean.calOut = et_dcsm_out.text.toString()
            bean.calIn = et_dcsm_in.text.toString()
            bean.calRound = et_dcsm_round.text.toString()
            bean.calTubeGap = et_dcsm_tube_gap.text.toString()
            bean.calRoundGap = et_dcsm_round_gap.text.toString()
            bean.calPunchPin = et_dcsm_punch_pin.text.toString()
            bean.calPunchPercent = et_dcsm_punch_percent.text.toString()
            if (isAdd) {
                list.add(bean)
            } else {
                list[position] = bean
            }
            saveObject(mContext, LOCAL_Data, SETTING_PLANS, data)
        }
    }
}