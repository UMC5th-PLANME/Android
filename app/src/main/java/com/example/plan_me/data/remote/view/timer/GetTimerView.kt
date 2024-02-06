package com.example.plan_me.data.remote.view.timer

import com.example.plan_me.data.remote.dto.timer.TimerSettingRes

interface GetTimerView {
    fun onGetTimerSuccess(response: TimerSettingRes)
    fun onGetTimerFailure(isSuccess: Boolean, code: String, message: String)
}