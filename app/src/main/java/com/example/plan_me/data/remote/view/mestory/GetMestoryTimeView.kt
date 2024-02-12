package com.example.plan_me.data.remote.view.mestory

import com.example.plan_me.data.remote.dto.mestory.GetMestoryTimeRes

interface GetMestoryTimeView {
    fun onGetMestoryTimeSuccess(response: GetMestoryTimeRes)
    fun onGetMestoryTimeFailure(isSuccess: Boolean, code: String, message: String)
}