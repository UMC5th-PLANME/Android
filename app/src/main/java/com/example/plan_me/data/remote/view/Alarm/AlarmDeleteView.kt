package com.example.plan_me.data.remote.view.Alarm

import com.example.plan_me.data.remote.dto.alarm.AlarmDeleteRes
import com.example.plan_me.data.remote.dto.auth.AutoLoginRes

interface AlarmDeleteView {
    fun onAlarmDeleteSuccess(response: AlarmDeleteRes)
    fun onAlarmDeleteFailure(isSuccess: Boolean, code: String, message: String)
}