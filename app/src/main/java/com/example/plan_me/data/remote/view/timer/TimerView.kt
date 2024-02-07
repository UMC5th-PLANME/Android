package com.example.plan_me.data.remote.view.timer

import com.example.plan_me.data.remote.dto.timer.TimerSettingRes

interface TimerView {
    fun onSetTimerSuccess(response: TimerSettingRes)
    fun onSetTimerFailure(isSuccess: Boolean, code: String, message: String)
}