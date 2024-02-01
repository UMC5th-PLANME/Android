package com.example.plan_me.data.remote.service.category

import android.util.Log
import com.example.plan_me.data.local.entity.category_input
import com.example.plan_me.data.remote.dto.auth.ProfileImageRes
import com.example.plan_me.data.remote.dto.category.AddCategoryRes
import com.example.plan_me.data.remote.retrofit.AuthRetrofitInterface
import com.example.plan_me.data.remote.retrofit.categoryRetrofitInterface
import com.example.plan_me.data.remote.view.auth.ProfileImageView
import com.example.plan_me.data.remote.view.auth.SignUpView
import com.example.plan_me.data.remote.view.auth.TermsView
import com.example.plan_me.data.remote.view.category.AddCategoryView
import com.example.plan_me.utils.getRetrofit
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryService  {

    private lateinit var addCategoryView: AddCategoryView

    fun setSignUpView(signUpView: SignUpView) {
        this.addCategoryView = addCategoryView
    }

    fun addCategoryFun(accessToken: String, category_input: category_input) {
        val CategoryService = getRetrofit().create(categoryRetrofitInterface::class.java)
        CategoryService.postCategory(accessToken, category_input).enqueue(object : Callback<AddCategoryRes> {
            override fun onResponse(call: Call<AddCategoryRes>, response: Response<AddCategoryRes>) {
                Log.d("PROFILE-IMG-SUCCESS", response.toString())
                val resp: AddCategoryRes = response.body()!!
                when(resp.code) {
                    "CATEGORY2001" -> Log.d("add category", "success")
                    else -> addCategoryView.onAddCategoryFailure(resp.isSuccess, resp.code, resp.message)
                }
            }
            override fun onFailure(call: Call<AddCategoryRes>, t: Throwable) {
                Log.d("PROFILE-IMG-FAILURE", t.toString())
            }
        })
    }
}