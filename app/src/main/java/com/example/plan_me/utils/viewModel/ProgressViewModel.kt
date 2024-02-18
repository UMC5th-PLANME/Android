package com.example.plan_me.utils.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class ProgressViewModel : ViewModel() {
    val _progress = MutableLiveData<Int>()  //프로그래스(0~100)
    val _hour = MutableLiveData<Int>()
    val _min = MutableLiveData<Int>()
    val _sec = MutableLiveData<Int>()
    val _break_hour = MutableLiveData<Int>()
    val _break_min = MutableLiveData<Int>()
    val _break_sec = MutableLiveData<Int>()
    val _repeat = MutableLiveData<Int>()
    val _time = MutableLiveData<String>()
    val _break_time = MutableLiveData<String>()
    val mills = MutableLiveData<Float>()

    // 현재 진행중인 Job 객체를 저장
    private var job: Job? = null

    init {
        _progress.value = 0
        _hour.value =0
        _min.value =0
        _sec.value =0
        _break_hour.value =0
        _break_sec.value =0
        _break_min.value =0
        _time.value = "00:00:00"
        _break_time.value = "00:00:00"
        mills.value = 0f
    }

    fun startProgress(hour :MutableLiveData<Int> , min :MutableLiveData<Int>, sec :MutableLiveData<Int> ) {
        val totalMilliseconds = ((hour.value ?: 0) * 60 + (min.value ?: 0)) * 60 * 1000
        if (job == null) {
            job = viewModelScope.launch(Dispatchers.IO) {
                while (mills.value!! <= totalMilliseconds) {
                    mills.postValue(mills.value!! + 50)
                    if (mills.value!! % 1000 == 0f) {
                        _progress.postValue(((mills.value!!/totalMilliseconds)*100).toInt())
                        sec.postValue(sec.value!! - 1)
                        if (sec.value!! == 0) {
                            min.postValue(min.value!! -1)
                            if (min.value!! == 0) {
                                hour.postValue(hour.value!!-1)
                                if (hour.value!! == 0) {
                                    initClear()
                                    stopProgress()
                                }
                                min.postValue(59)
                            }
                            sec.postValue(59)
                        }
                        initTime(hour, min, sec)
                    }

                    delay(50)
                }
            }
        }

    }

    fun stopProgress() {
        job?.cancel()
        job = null
    }
    fun initProgress() {
        _progress.value = 0
    }

    fun initTime(hour :MutableLiveData<Int> , min :MutableLiveData<Int>, sec :MutableLiveData<Int>) {
        _time.postValue(String.format("%02d:%02d:%02d", hour.value, min.value, sec.value))
    }
    fun initClear() {
        _time.postValue("CLEAR")
    }
    fun clear() {
        _progress.value = 0
        _hour.value =0
        _min.value =0
        _sec.value =0
        _break_hour.value =0
        _break_sec.value =0
        _break_min.value =0
        _time.value = "00:00:00"
        _break_time.value = "00:00:00"
    }
}
