package com.example.plan_me.data.remote.view.auth

import com.example.plan_me.data.remote.dto.auth.TermsRes

interface TermsView {
    fun onSetTermsSuccess(response: TermsRes)
    fun onSetTermsFailure(isSuccess: Boolean, code: String, message: String)
}