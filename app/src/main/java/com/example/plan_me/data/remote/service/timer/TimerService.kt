package com.example.plan_me.data.remote.service.timer

import android.util.Log
import com.example.plan_me.data.remote.dto.timer.TimerSettingReq
import com.example.plan_me.data.remote.dto.timer.TimerSettingRes
import com.example.plan_me.data.remote.retrofit.TimerRetrofitInterface
import com.example.plan_me.data.remote.view.timer.TimerView
import com.example.plan_me.utils.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TimerService {
    private lateinit var timerView: TimerView

    fun setTimerView(timerView: TimerView) {
        this.timerView = timerView
    }

    fun setTimer(accessToken: String, categoryId: Int, timerSettingReq: TimerSettingReq) {
        val timerService = getRetrofit().create(TimerRetrofitInterface::class.java)
        timerService.postTimerSetting(accessToken, categoryId, timerSettingReq).enqueue(object : Callback<TimerSettingRes> {
            override fun onResponse(
                call: Call<TimerSettingRes>,
                response: Response<TimerSettingRes>
            ) {
                Log.d("TimerSettting-SUCCESS", response.toString())
                val resp: TimerSettingRes = response.body()!!
                when(resp.code) {
                    "FOCUS2011", "FOCUS2012" -> timerView.onSetTimerSuccess(resp)
                    else -> timerView.onSetTimerFailure(resp.isSuccess, resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<TimerSettingRes>, t: Throwable) {
                Log.d("TimerSettting-FAILURE", t.toString())
            }
        })
    }
}