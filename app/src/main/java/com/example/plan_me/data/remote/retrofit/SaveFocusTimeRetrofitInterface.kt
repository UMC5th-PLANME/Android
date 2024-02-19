package com.example.plan_me.data.remote.retrofit

import com.example.plan_me.data.remote.dto.mestory.SaveFocusTimeRes
import com.example.plan_me.data.remote.dto.mestory.TotalFocusTime
import retrofit2.Call
import retrofit2.http.*
interface SaveFocusTimeRetrofitInterface {
    @POST("/api/meStory/{categoryId}")
    fun postFocusTime(@Header("Authorization") Authorization: String, @Path("categoryId") categoryId: Int, @Body totalFocusTime: TotalFocusTime): Call<SaveFocusTimeRes>
}