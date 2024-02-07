package com.example.plan_me.data.remote.service.schedule

import android.util.Log
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
import com.example.plan_me.data.remote.retrofit.categoryRetrofitInterface
import com.example.plan_me.data.remote.retrofit.scheduleRetrofitInterface
import com.example.plan_me.data.remote.view.category.AddCategoryView
import com.example.plan_me.data.remote.view.category.AllCategoryView
import com.example.plan_me.data.remote.view.category.DeleteCategoryView
import com.example.plan_me.data.remote.view.category.ModifyCategoryView
import com.example.plan_me.data.remote.view.category.ModifyStatusCategoryView
import com.example.plan_me.data.remote.view.category.OneCategoryView
import com.example.plan_me.data.remote.view.schedule.AddScheduleView
import com.example.plan_me.data.remote.view.schedule.AllScheduleView
import com.example.plan_me.data.remote.view.schedule.DeleteScheduleView
import com.example.plan_me.data.remote.view.schedule.ModifyScheduleView
import com.example.plan_me.data.remote.view.schedule.OneScheduleView
import com.example.plan_me.utils.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScheduleService  {

    private lateinit var addScheduleView: AddScheduleView
    private lateinit var allScheduleView: AllScheduleView
    private lateinit var deleteScheduleView: DeleteScheduleView
    private lateinit var modifyScheduleView: ModifyScheduleView
    private lateinit var oneScheduleView: OneScheduleView
    val ScheduleService = getRetrofit().create(scheduleRetrofitInterface::class.java)

    fun setAddScheduleView(addScheduleView: AddScheduleView) {
        this.addScheduleView = addScheduleView
    }
    fun setAllScheduleView(allScheduleView: AllScheduleView) {
        this.allScheduleView = allScheduleView
    }

    fun setDeleteScheduleView(deleteScheduleView: DeleteScheduleView) {
        this.deleteScheduleView = deleteScheduleView
    }

    fun setOneScheduleView(modifyScheduleView: ModifyScheduleView) {
        this.modifyScheduleView = modifyScheduleView
    }

    fun setModifyScheduleView(oneScheduleView: OneScheduleView) {
        this.oneScheduleView = oneScheduleView
    }
    fun addScheduleFun(accessToken: String, schedule_input: Schedule_input) {
        ScheduleService.addSchedule(accessToken, schedule_input).enqueue(object : Callback<AddScheduleRes> {
            override fun onResponse(call: Call<AddScheduleRes>, response: Response<AddScheduleRes>) {
                Log.d("add schedule response", response.body().toString())
                val resp: AddScheduleRes = response.body()!!
                when(resp.code) {
                    "COMMON200" -> addScheduleView.onAddScheduleSuccess(resp)
                    else -> addScheduleView.onAddScheduleFailure(resp)
                }
            }
            override fun onFailure(call: Call<AddScheduleRes>, t: Throwable) {
                Log.d("add category failure", t.toString())
            }
        })
    }

    fun getScheduleAllFun(accessToken: String) {
        ScheduleService.getScheduleAll(accessToken).enqueue(object :Callback<AllScheduleRes>{
            override fun onResponse(
                call: Call<AllScheduleRes>,
                response: Response<AllScheduleRes>
            ) {
                val resp = response.body()!!
                when(resp.code) {
                    "SCHEDULE2002" -> allScheduleView.onAllScheduleSuccess(resp)
                    else -> allScheduleView.onAllScheduleFailure(resp)
                }
            }

            override fun onFailure(call: Call<AllScheduleRes>, t: Throwable) {
                Log.d("all category failure", t.toString())
            }
        })
    }

    fun deleteScheduleFun(accessToken: String, schedule_id : Int) {
        ScheduleService.deleteSchedule(schedule_id, accessToken).enqueue(object :Callback<DeleteScheduleRes>{
            override fun onResponse(
                call: Call<DeleteScheduleRes>,
                response: Response<DeleteScheduleRes>
            ) {
                val resp = response.body()!!
                when(resp.code) {
                    "CATEGORY2005" -> deleteScheduleView.onDeleteScheduleSuccess(resp)
                    else -> deleteScheduleView.onDeleteScheduleFailure(resp)
                }
            }

            override fun onFailure(call: Call<DeleteScheduleRes>, t: Throwable) {
                Log.d("delete category failure", t.toString())
            }
        })
    }

    fun getOneScheduleFun(accessToken: String, schedule_id : Int) {
        ScheduleService.getScheduleOne(schedule_id, accessToken).enqueue(object :Callback<OneScheduleRes>{
            override fun onResponse(
                call: Call<OneScheduleRes>,
                response: Response<OneScheduleRes>
            ) {
                val resp = response.body()!!
                when(resp.code) {
                    "CATEGORY2005" -> oneScheduleView.onOneScheduleSuccess(resp)
                    else -> oneScheduleView.onOneScheduleFailure(resp)
                }
            }

            override fun onFailure(call: Call<OneScheduleRes>, t: Throwable) {
                Log.d("get category failure", t.toString())
            }
        })
    }

    fun modifyScheduleFun(accessToken: String, schedule_id : Int, schedule_input: Schedule_input) {
        ScheduleService.modifySchedule(schedule_id, accessToken, schedule_input).enqueue(object :Callback<ModifyScheduleRes>{
            override fun onResponse(
                call: Call<ModifyScheduleRes>,
                response: Response<ModifyScheduleRes>
            ) {
                val resp = response.body()!!
                when(resp.code) {
                    "CATEGORY2005" -> modifyScheduleView.onModifyScheduleSuccess(resp)
                    else -> modifyScheduleView.onModifyScheduleFailure(resp)
                }
            }

            override fun onFailure(call: Call<ModifyScheduleRes>, t: Throwable) {
                Log.d("modify category failure", t.toString())
            }
        })
    }


}