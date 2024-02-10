package com.example.plan_me.data.remote.retrofit

import com.example.plan_me.data.remote.dto.timer.TimerSettingReq
import com.example.plan_me.data.remote.dto.timer.TimerSettingRes
import retrofit2.Call
import retrofit2.http.*
interface TimerRetrofitInterface {
    @POST("/api/focus/{categoryId}")
    fun postTimerSetting(@Header("accessToken") accessToken: String, @Path("categoryId") categoryId: Int, @Body timerSettingReq: TimerSettingReq): Call<TimerSettingRes>

}