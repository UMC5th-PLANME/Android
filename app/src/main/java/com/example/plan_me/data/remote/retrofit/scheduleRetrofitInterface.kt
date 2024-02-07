package com.example.plan_me.data.remote.retrofit

import com.example.plan_me.data.local.entity.Schedule_input
import com.example.plan_me.data.remote.dto.schedule.AddScheduleRes
import com.example.plan_me.data.remote.dto.schedule.AllScheduleRes
import com.example.plan_me.data.remote.dto.schedule.DeleteScheduleRes
import com.example.plan_me.data.remote.dto.schedule.ModifyScheduleRes
import com.example.plan_me.data.remote.dto.schedule.OneScheduleRes
import retrofit2.Call
import retrofit2.http.*

interface scheduleRetrofitInterface {
    @POST("/api/schedule")
    fun addSchedule(@Header("Authorization") Authorization: String, @Body schedule_input: Schedule_input) : Call<AddScheduleRes>

    @DELETE("/api/schedule/{schedule_id}")
    fun deleteSchedule(@Path("schedule_id") schedule_id: Int, @Header("Authorization") Authorization: String) : Call<DeleteScheduleRes>

    @GET("/api/schedule")
    fun getScheduleAll(@Header("Authorization") Authorization:String) :Call<AllScheduleRes>

    @GET("/api/schedule/{schedule_id}")
    fun getScheduleOne(@Path("schedule_id") schedule_id: Int,@Header("Authorization") Authorization:String) :Call<OneScheduleRes>

    @PATCH("/api/schedule/{schedule_id}")
    fun modifySchedule(@Path("schedule_id") schedule_id: Int,@Header("Authorization") Authorization:String, schedule_input: Schedule_input) :Call<ModifyScheduleRes>


}