package com.example.plan_me.utils.viewModel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.plan_me.data.remote.dto.timer.TimerSettingReq
import com.example.plan_me.data.remote.dto.timer.TimerSettingRes
import com.example.plan_me.data.remote.service.timer.TimerService
import com.example.plan_me.data.remote.view.timer.GetTimerView
import com.example.plan_me.data.remote.view.timer.TimerView

class TimerViewModel(private val sharedPreferences: SharedPreferences): ViewModel(), TimerView, GetTimerView {
    private val focusML = MutableLiveData<String>().apply {
        Log.d("init focus mutable livedata", "focus 라이브 데이터 초기화")
        setValue("10:00:00")
    }

    private val breakML = MutableLiveData<String>().apply {
        Log.d("init break mutable livedata", "break 라이브 데이터 초기화")
        setValue("10:00:00")
    }

    private val repeatCntML = MutableLiveData<Int>().apply {
        Log.d("init repeatCnt mutable livedata", "repeatCnt 라이브 데이터 초기화")
        setValue(0)
    }

    fun getFocus(): LiveData<String> = focusML
    fun getBreak(): LiveData<String> = breakML
    fun getRepeat(): LiveData<Int> = repeatCntML

    fun initFocusValue(hour: Int, minute: Int, second: Int) {
        Log.d("update focus mutable livedata", "initFocusValue() - 라이브 데이터 변경")

        if (minute < 10) {
            if (second < 10) {
                focusML.postValue("$hour:0$minute:0$second")
            } else {
                focusML.postValue("$hour:0$minute:$second")
            }
        } else {
            if (second < 10) {
                focusML.postValue("$hour:$minute:0$second")
            } else {
                focusML.postValue("$hour:$minute:$second")
            }
        }
    }

    fun initBreakValue(hour: Int, minute: Int, second: Int) {
        Log.d("update break mutable livedata", "initBreakValue() - 라이브 데이터 변경")

        if (minute < 10) {
            if (second < 10) {
                breakML.postValue("$hour:0$minute:0$second")
            } else {
                breakML.postValue("$hour:0$minute:$second")
            }
        } else {
            if (second < 10) {
                breakML.postValue("$hour:$minute:0$second")
            } else {
                breakML.postValue("$hour:$minute:$second")
            }
        }
    }

    fun initRepeatCntValue(repeatCnt: Int) {
        Log.d("update break mutable livedata", "initBreakValue() - 라이브 데이터 변경")

        repeatCntML.postValue(repeatCnt)
    }

    fun setTimer() {
        val access_token = "Bearer " + sharedPreferences.getString("getAccessToken", "")
        val categoryId = sharedPreferences.getInt("category", 0)
        val timerSettingReq = TimerSettingReq(focusML, breakML, repeatCntML)

        val timerService = TimerService()
        timerService.setTimerView(this@TimerViewModel)
        timerService.setTimer(access_token, categoryId, timerSettingReq)
    }

    fun getTimer() {
        val access_token = "Bearer " + sharedPreferences.getString("getAccessToken", "")
        val categoryId = sharedPreferences.getInt("category", 0)

        val timerService = TimerService()
        timerService.getTimerView(this@TimerViewModel)
        timerService.getTimer(access_token, categoryId)
    }

    override fun onSetTimerSuccess(response: TimerSettingRes) {
        Log.d("TIMER 설정", response.result.toString())
        setTimer()
    }

    override fun onSetTimerFailure(isSuccess: Boolean, code: String, message: String) {
        Log.d("TIMER 설정 실패", message)
    }

    override fun onGetTimerSuccess(response: TimerSettingRes) {
        Log.d("TIMER VIEW 설정", response.result.toString())
        getTimer()
    }

    override fun onGetTimerFailure(isSuccess: Boolean, code: String, message: String) {
        Log.d("TIMER VIEW 설정 실패", message)
    }
}