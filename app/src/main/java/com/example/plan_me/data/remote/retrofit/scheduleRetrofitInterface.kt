package com.example.plan_me.data.remote.retrofit

import com.example.plan_me.data.local.entity.Category_input
import com.example.plan_me.data.local.entity.Schedule_input
import com.example.plan_me.data.remote.dto.category.AddCategoryRes
import com.example.plan_me.data.remote.dto.category.AllCategoryRes
import com.example.plan_me.data.remote.dto.category.DeleteCategoryRes
import com.example.plan_me.data.remote.dto.category.ModifyCategoryRes
import com.example.plan_me.data.remote.dto.category.ModifyStatusCategoryRes
import com.example.plan_me.data.remote.dto.category.OneCategoryRes
import com.example.plan_me.data.remote.dto.schedule.AddScheduleRes
import com.example.plan_me.data.remote.dto.schedule.AllScheduleRes
import com.example.plan_me.data.remote.dto.schedule.DeleteScheduleRes
import com.example.plan_me.data.remote.dto.schedule.ModifyScheduleRes
import com.example.plan_me.data.remote.dto.schedule.OneScheduleRes
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

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