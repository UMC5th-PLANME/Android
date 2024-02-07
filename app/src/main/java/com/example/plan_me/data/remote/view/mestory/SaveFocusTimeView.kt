package com.example.plan_me.data.remote.view.mestory

import com.example.plan_me.data.remote.dto.mestory.SaveFocusTimeRes

interface SaveFocusTimeView {
    fun onSaveFocusTimeSuccess(response: SaveFocusTimeRes)
    fun onSaveFocusTimeFailure(isSuccess: Boolean, code: String, message: String)
}