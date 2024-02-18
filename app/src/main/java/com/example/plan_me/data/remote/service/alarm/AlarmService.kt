package com.example.plan_me.data.remote.service.alarm

import android.util.Log
import com.example.plan_me.data.remote.dto.alarm.AlarmDeleteRes
import com.example.plan_me.data.remote.dto.alarm.AlarmGetRes
import com.example.plan_me.data.remote.dto.alarm.AlarmPostRes
import com.example.plan_me.data.remote.dto.auth.ProfileImageRes
import com.example.plan_me.data.remote.retrofit.AlarmRetrofitInterface
import com.example.plan_me.data.remote.retrofit.AuthRetrofitInterface
import com.example.plan_me.data.remote.view.Alarm.AlarmDeleteView
import com.example.plan_me.data.remote.view.Alarm.AlarmGetView
import com.example.plan_me.data.remote.view.Alarm.AlarmPostView
import com.example.plan_me.data.remote.view.auth.ProfileImageView
import com.example.plan_me.utils.getRetrofit
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlarmService {
    private lateinit var alarmGetView: AlarmGetView
    private lateinit var alarmPostView: AlarmPostView
    private lateinit var alarmDeleteView: AlarmDeleteView

    fun setAlarmGetView(alarmGetView: AlarmGetView) {
        this.alarmGetView = alarmGetView
    }
    fun setAlarmPostView(alarmPostView: AlarmPostView) {
        this.alarmPostView = alarmPostView
    }
    fun setAlarmDeleteView(alarmDeleteView: AlarmDeleteView) {
        this.alarmDeleteView = alarmDeleteView
    }

    fun getAlarmList(accessToken: String, schedule_id :Int) {
        val alarmService = getRetrofit().create(AlarmRetrofitInterface::class.java)
        alarmService.getAlarm(accessToken, schedule_id).enqueue(object : Callback<AlarmGetRes> {
            override fun onResponse(call: Call<AlarmGetRes>, response: Response<AlarmGetRes>) {
                if (response.isSuccessful) {
                    val resp: AlarmGetRes? = response.body()
                    if (resp != null) {
                        when (resp.code) {
                            "IMAGE2001" -> alarmGetView.onAlarmGetSuccess(resp)
                            else -> alarmGetView.onAlarmGetFailure(resp.isSuccess, resp.code, resp.message)
                        }
                    } else {
                        Log.e("PROFILE-IMG-SUCCESS", "Response body is null")
                    }
                } else {
                    Log.e("PROFILE-IMG-SUCCESS", "Response not successful: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<AlarmGetRes>, t: Throwable) {
                Log.d("PROFILE-IMG-FAILURE", t.toString())
            }
        })
    }

    fun postAlarmList(accessToken: String, schedule_id :Int) {
        val alarmService = getRetrofit().create(AlarmRetrofitInterface::class.java)
        alarmService.postAlarm(accessToken, schedule_id).enqueue(object : Callback<AlarmPostRes> {
            override fun onResponse(call: Call<AlarmPostRes>, response: Response<AlarmPostRes>) {
                if (response.isSuccessful) {
                    val resp: AlarmPostRes? = response.body()
                    if (resp != null) {
                        when (resp.code) {
                            "IMAGE2001" -> alarmPostView.onAlarmPostSuccess(resp)
                            else -> alarmPostView.onAlarmPostFailure(resp.isSuccess, resp.code, resp.message)
                        }
                    } else {
                        Log.e("PROFILE-IMG-SUCCESS", "Response body is null")
                    }
                } else {
                    Log.e("PROFILE-IMG-SUCCESS", "Response not successful: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<AlarmPostRes>, t: Throwable) {
                Log.d("PROFILE-IMG-FAILURE", t.toString())
            }
        })
    }
    fun deleteAlarmList(accessToken: String, schedule_id :Int) {
        val alarmService = getRetrofit().create(AlarmRetrofitInterface::class.java)
        alarmService.deleteAlarm(accessToken, schedule_id).enqueue(object : Callback<AlarmDeleteRes> {
            override fun onResponse(call: Call<AlarmDeleteRes>, response: Response<AlarmDeleteRes>) {
                if (response.isSuccessful) {
                    val resp: AlarmDeleteRes? = response.body()
                    if (resp != null) {
                        when (resp.code) {
                            "IMAGE2001" -> alarmDeleteView.onAlarmDeleteSuccess(resp)
                            else -> alarmDeleteView.onAlarmDeleteFailure(resp.isSuccess, resp.code, resp.message)
                        }
                    } else {
                        Log.e("PROFILE-IMG-SUCCESS", "Response body is null")
                    }
                } else {
                    Log.e("PROFILE-IMG-SUCCESS", "Response not successful: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<AlarmDeleteRes>, t: Throwable) {
                Log.d("PROFILE-IMG-FAILURE", t.toString())
            }
        })
    }
}