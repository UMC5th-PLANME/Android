package com.example.plan_me.data.remote.view.auth

import com.example.plan_me.data.remote.dto.auth.MemberRes

interface LookUpMemberView {
    fun onGetMemberSuccess(response: MemberRes)
    fun onGetMemberFailure(isSuccess: Boolean, code: String, message: String)
}