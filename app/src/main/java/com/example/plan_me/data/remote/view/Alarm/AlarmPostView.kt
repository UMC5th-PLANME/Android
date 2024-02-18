package com.example.plan_me.data.remote.view.Alarm

import com.example.plan_me.data.remote.dto.alarm.AlarmPostRes
import com.example.plan_me.data.remote.dto.auth.AutoLoginRes

interface AlarmPostView {
    fun onAlarmPostSuccess(response: AlarmPostRes)
    fun onAlarmPostFailure(isSuccess: Boolean, code: String, message: String)
}