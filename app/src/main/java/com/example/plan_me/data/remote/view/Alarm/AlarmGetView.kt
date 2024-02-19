package com.example.plan_me.data.remote.view.Alarm

import com.example.plan_me.data.remote.dto.alarm.AlarmGetRes

interface AlarmGetView {
    fun onAlarmGetSuccess(response: AlarmGetRes)
    fun onAlarmGetFailure(isSuccess: Boolean, code: String, message: String)
}