package com.example.plan_me.data.remote.view.Alarm

import com.example.plan_me.data.remote.dto.alarm.AlarmPostRes
import com.example.plan_me.data.remote.dto.auth.AutoLoginRes
import java.time.LocalDate

interface AlarmPostView {
    fun onAlarmPostSuccess(response: AlarmPostRes, date:LocalDate)
    fun onAlarmPostFailure(isSuccess: Boolean, code: String, message: String)
}