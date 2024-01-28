package com.example.plan_me.data.remote.view.auth

import com.example.plan_me.data.remote.dto.auth.ProfileImageRes

interface ProfileImageView {
    fun onSetImgSuccess(response: ProfileImageRes)
    fun onSetImgFailure(isSuccess: Boolean, code: String, message: String)
}