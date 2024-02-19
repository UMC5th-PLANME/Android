package com.example.plan_me.data.remote.view.timer

import com.example.plan_me.data.remote.dto.timer.GetTimerRes

interface GetTimerView {
    fun onGetTimerSuccess(response: GetTimerRes)
    fun onGetTimerFailure(isSuccess: Boolean, code: String, message: String)
}