package com.example.plan_me.ui.dialog

interface DialogTimerPickInterface {
    fun onTimerSettingConfirm(focusMin : Int, breakMin : Int, repeatCount : Int )
    fun onTimerSettingCancel()
}

