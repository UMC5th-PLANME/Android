package com.example.plan_me.ui.timer

interface TimerSettingListener {
    fun onSettingConfirmed(focusTime: Int)
}

// DialogTimerSettingFragment 내부에서 설정 변경 시 호출 되는 메서드
private var settingConfirmedListener: TimerSettingListener? = null

fun setOnSettingConfirmedListener(listener: TimerSettingListener) {
    settingConfirmedListener = listener
}

// 설정 변경 시 호출
private fun notifySettingConfirmed(focusTime: Int) {
    settingConfirmedListener?.onSettingConfirmed(focusTime)
}