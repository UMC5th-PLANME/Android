package com.example.plan_me.data.remote.view.auth

import com.example.plan_me.data.remote.dto.auth.DeleteMemberRes

interface DeleteMemberView {
    fun onDelMemberSuccess(response: DeleteMemberRes)
    fun onDelMemberFailure(isSuccess: Boolean, code: String, message: String)
}