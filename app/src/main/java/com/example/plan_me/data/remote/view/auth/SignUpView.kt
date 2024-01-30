package com.example.plan_me.data.remote.view.auth

import com.example.plan_me.data.remote.dto.auth.SignUpRes

interface SignUpView {
    fun onSetSignUpSuccess(response: SignUpRes)
    fun onSetSignUpFailure(isSuccess: Boolean, code: String, message: String)
}