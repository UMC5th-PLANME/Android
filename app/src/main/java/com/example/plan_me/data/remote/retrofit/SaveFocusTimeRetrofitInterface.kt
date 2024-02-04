package com.example.plan_me.data.remote.retrofit

import com.example.plan_me.data.remote.dto.mestory.SaveFocusTimeReq
import com.example.plan_me.data.remote.dto.mestory.SaveFocusTimeRes
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface SaveFocusTimeRetrofitInterface {
    @POST("/api/meStory/{categoryId}")
    fun postFocusTime(@Header("accessToken") accessToken: String, @Path("categoryId") categoryId: Int, @Body saveFocusTimeReq: SaveFocusTimeReq): Call<SaveFocusTimeRes>

}