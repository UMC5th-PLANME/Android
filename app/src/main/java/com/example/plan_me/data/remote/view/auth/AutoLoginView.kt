package com.example.plan_me.data.remote.view.auth

import com.example.plan_me.data.remote.dto.auth.AutoLoginRes

interface AutoLoginView {
    fun onGetAutoLoginSuccess(response: AutoLoginRes)
    fun onGetAutoLoginFailure(isSuccess: Boolean, code: String, message: String)
}