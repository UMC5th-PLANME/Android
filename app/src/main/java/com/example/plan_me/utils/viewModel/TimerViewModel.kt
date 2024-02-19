package com.example.plan_me.utils.viewModel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.plan_me.data.remote.dto.timer.GetTimerRes
import com.example.plan_me.data.remote.service.timer.TimerService
import com.example.plan_me.data.remote.view.timer.GetTimerView

class TimerViewModel(private val getResSharedPreferences: SharedPreferences, private val categoryIdSharedPreferences: SharedPreferences): ViewModel(), GetTimerView {
    val focusML = MutableLiveData<String>().apply {
        Log.d("init focus mutable livedata", "focus 라이브 데이터 초기화")
        setValue("00:00:00")
    }

    val breakML = MutableLiveData<String>().apply {
        Log.d("init break mutable livedata", "break 라이브 데이터 초기화")
        setValue("00:00:00")
    }

    val repeatCntML = MutableLiveData<Int>().apply {
        Log.d("init repeatCnt mutable livedata", "repeatCnt 라이브 데이터 초기화")
        setValue(1)
    }

    fun getFocus(): LiveData<String> = focusML
    fun getBreak(): LiveData<String> = breakML
    fun getRepeat(): LiveData<Int> = repeatCntML

    fun getTimer() {
        val access_token = "Bearer " + getResSharedPreferences.getString("getAccessToken", "")
        val categoryId = categoryIdSharedPreferences.getInt("category_id", 0)

        val timerService = TimerService()
        timerService.getTimerView(this@TimerViewModel)
        timerService.getTimer(access_token, categoryId)
    }

    override fun onGetTimerSuccess(response: GetTimerRes) {
        Log.d("TIMER VIEW 설정", response.result.toString())

        focusML.postValue(response.result.focusTime)
        breakML.postValue(response.result.breakTime)
        repeatCntML.postValue(response.result.repeatCnt)
    }

    override fun onGetTimerFailure(isSuccess: Boolean, code: String, message: String) {
        Log.d("TIMER VIEW 설정 실패", message)

        focusML.postValue("00:00:00")
        breakML.postValue("00:00:00")
        repeatCntML.postValue(1)
    }
}