package com.example.plan_me.data.remote.view.auth

import com.example.plan_me.data.remote.dto.auth.ChangeMemberRes

interface ChangeProfileView {
    fun onSetChangeProfileSuccess(response: ChangeMemberRes)
    fun onSetChangeProfileFailure(isSuccess: Boolean, code: String, message: String)
}