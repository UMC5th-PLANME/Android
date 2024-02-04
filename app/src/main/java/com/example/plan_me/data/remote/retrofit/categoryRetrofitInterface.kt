package com.example.plan_me.data.remote.retrofit

import com.example.plan_me.data.local.entity.Category_input
import com.example.plan_me.data.remote.dto.category.AddCategoryRes
import com.example.plan_me.data.remote.dto.category.AllCategoryRes
import com.example.plan_me.data.remote.dto.category.DeleteCategoryRes
import com.example.plan_me.data.remote.dto.category.ModifyCategoryRes
import com.example.plan_me.data.remote.dto.category.ModifyStatusCategoryRes
import com.example.plan_me.data.remote.dto.category.OneCategoryRes
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface categoryRetrofitInterface {
    @POST("/api/category")
    fun postCategory(@Header("Authorization") Authorization: String, @Body category_input: Category_input) : Call<AddCategoryRes>

    @DELETE("/api/category/{categoryId}")
    fun deleteCategory(@Path("categoryId") categoryId: Int, @Header("Authorization") Authorization: String) : Call<DeleteCategoryRes>

    @GET("/api/category/all")
    fun getCategoryAll(@Header("Authorization") Authorization:String) :Call<AllCategoryRes>

    @GET("/api/category/{categoryId}")
    fun getCategoryOne(@Path("categoryId") categoryId: Int,@Header("Authorization") Authorization:String) :Call<OneCategoryRes>

    @PATCH("/api/category/{categoryId}")
    fun modifyCategory(@Path("categoryId") categoryId: Int,@Header("Authorization") Authorization:String,@Body category_input: Category_input ) :Call<ModifyCategoryRes>

    @PATCH("/api/category/status/{categoryId}")
    fun modifyStatusCategory(@Path("categoryId") categoryId: Int,@Header("Authorization") Authorization:String) :Call<ModifyStatusCategoryRes>
}