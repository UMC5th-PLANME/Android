package com.example.plan_me.data.remote.service.timer

import android.util.Log
import com.example.plan_me.data.remote.dto.timer.GetTimerRes
import com.example.plan_me.data.remote.dto.timer.TimerSettingReq
import com.example.plan_me.data.remote.dto.timer.TimerSettingRes
import com.example.plan_me.data.remote.retrofit.TimerRetrofitInterface
import com.example.plan_me.data.remote.view.timer.GetTimerView
import com.example.plan_me.data.remote.view.timer.TimerView
import com.example.plan_me.utils.getRetrofit
import com.example.plan_me.utils.viewModel.TimerViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TimerService {
    private lateinit var timerView: TimerView
    private lateinit var getTimerView: GetTimerView

    fun setTimerView(timerView: TimerView) {
        this.timerView = timerView
    }

    fun getTimerView(getTimerView: GetTimerView) {
        this.getTimerView = getTimerView
    }

    fun setTimer(accessToken: String, categoryId: Int, timerSettingReq: TimerSettingReq) {
        val timerService = getRetrofit().create(TimerRetrofitInterface::class.java)
        timerService.postTimerSetting(accessToken, categoryId, timerSettingReq).enqueue(object : Callback<TimerSettingRes> {
            override fun onResponse(
                call: Call<TimerSettingRes>,
                response: Response<TimerSettingRes>
            ) {
                Log.d("SET-TIME-SUCCESS", response.toString())
                val resp: TimerSettingRes = response.body()!!
                when(resp.code) {
                    "FOCUS2011", "FOCUS2012" -> timerView.onSetTimerSuccess(resp)
                    else -> timerView.onSetTimerFailure(resp.isSuccess, resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<TimerSettingRes>, t: Throwable) {
                Log.d("SET-TIME-FAILURE", t.toString())
            }
        })
    }

    fun getTimer(accessToken: String, categoryId: Int) {
        val timerService = getRetrofit().create(TimerRetrofitInterface::class.java)
        timerService.getTimerSetting(accessToken, categoryId).enqueue(object : Callback<GetTimerRes> {
            override fun onResponse(call: Call<GetTimerRes>, response: Response<GetTimerRes>) {
                if(response.isSuccessful) {
                    val resp: GetTimerRes? = response.body()
                    if (resp != null) {
                        when (resp.code) {
                            "FOCUS2001" -> getTimerView.onGetTimerSuccess(resp)
                            else -> getTimerView.onGetTimerFailure(resp.isSuccess, resp.code, resp.message)
                        }
                    } else {
                        Log.e("GET-TIME-SUCCESS", "Response body is null")
                    }
                } else {
                    Log.e("GET-TIME-SUCCESS", "Response not successful: ${response.code()}")
                    getTimerView.onGetTimerFailure(true, "", "")
                }
            }

            override fun onFailure(call: Call<GetTimerRes>, t: Throwable) {
                Log.d("GET-TIME-FAILURE", t.toString())
            }
        })
    }
}