package com.example.plan_me.data.remote.retrofit

import com.example.plan_me.data.local.entity.category_input
import com.example.plan_me.data.remote.dto.category.AddCategoryRes
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface categoryRetrofitInterface {
    @POST("/api/category")
    fun postCategory(@Header("authorizationToken") authorizationToken: String, @Body category_input: category_input) : Call<AddCategoryRes>
}