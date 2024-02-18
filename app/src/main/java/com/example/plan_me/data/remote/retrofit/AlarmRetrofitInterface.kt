package com.example.plan_me.data.remote.retrofit

import com.example.plan_me.data.local.entity.EditProfile
import com.example.plan_me.data.local.entity.Member
import com.example.plan_me.data.remote.dto.alarm.AlarmDeleteRes
import com.example.plan_me.data.remote.dto.alarm.AlarmGetRes
import com.example.plan_me.data.remote.dto.alarm.AlarmPostRes
import com.example.plan_me.data.remote.dto.auth.AutoLoginRes
import com.example.plan_me.data.remote.dto.auth.ChangeMemberRes
import com.example.plan_me.data.remote.dto.auth.DeleteMemberRes
import com.example.plan_me.data.remote.dto.auth.MemberRes
import com.example.plan_me.data.remote.dto.auth.ProfileImageRes
import com.example.plan_me.data.remote.dto.auth.SignUpRes
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface AlarmRetrofitInterface {
    @GET("/api/Alarm/{schedule_id}")
    fun getAlarm(@Header("Authorization") Authorization: String, @Path("schedule_id") schedule_id: Int): Call<AlarmGetRes>

    // 회원가입
    @POST("/api/Alarm/{schedule_id}")
    fun postAlarm(@Header("Authorization") Authorization: String, @Path("schedule_id") schedule_id: Int): Call<AlarmPostRes>


    @DELETE("/api/Alarm/{schedule_id}")
    fun deleteAlarm(@Header("Authorization") Authorization: String, @Path("schedule_id") schedule_id: Int): Call<AlarmDeleteRes>

}