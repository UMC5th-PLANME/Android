package com.example.plan_me.data.remote.dto.mestory

import com.example.plan_me.data.remote.dto.auth.MemberId
import com.example.plan_me.data.remote.dto.mestory.GetMestoryTimeRes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface MestoryTimeRetrofitInterface {
    @GET("/api/meStory/{memberId}/{date}")
    fun getMestoryTime(@Header("accessToken") accessToken: String,
                       @Path("memberId") memberId: MemberId,
                       @Path("date") date: String)
    : Call<GetMestoryTimeRes>
}