package com.example.plan_me.data.remote.view.auth

import com.example.plan_me.data.remote.dto.auth.LoginRes

interface LoginView {
    fun onGetLoginSuccess(response: LoginRes)
    fun onGetLoginFailure(isSuccess: Boolean, code: String, message: String)
}