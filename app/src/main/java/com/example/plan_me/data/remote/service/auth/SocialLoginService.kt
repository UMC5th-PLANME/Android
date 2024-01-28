package com.example.plan_me.data.remote.service.auth

import android.util.Log
import com.example.plan_me.data.remote.dto.auth.LoginRes
import com.example.plan_me.data.remote.retrofit.AuthRetrofitInterface
import com.example.plan_me.data.remote.view.auth.LoginView
import com.example.plan_me.utils.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SocialLoginService {
    private lateinit var loginView: LoginView

    fun setLoginView(loginView: LoginView) {
        this.loginView = loginView
    }

    fun getSocialLogin(provider: String) {
        val loginService = getRetrofit().create(AuthRetrofitInterface::class.java)
        loginService.getSocialLogin(provider).enqueue(object : Callback<LoginRes> {
            override fun onResponse(call: Call<LoginRes>, response: Response<LoginRes>) {
                Log.d("LOGIN-SUCCESS", response.toString())
                val resp: LoginRes = response.body()!!
                when(resp.code) {
                    // 코드 미정 (임시 설정)
                    "login" -> loginView.onGetLoginSuccess(resp)
                    else -> loginView.onGetLoginFailure(resp.isSuccess, resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<LoginRes>, t: Throwable) {
                Log.d("LOGIN-FAILURE", t.toString())
            }
        })
    }
}