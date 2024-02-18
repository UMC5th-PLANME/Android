package com.example.plan_me.utils.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class ProgressViewModel : ViewModel() {
    val _progress = MutableLiveData<Int>()  //프로그래스(0~100)

    // 현재 진행중인 Job 객체를 저장
    private var job: Job? = null

    init {
        _progress.value = 0
    }

    fun startProgress() {
        if (job == null) {
            job = viewModelScope.launch(Dispatchers.IO) {
                while (_progress.value!! <= 100) {
                    delay(50) //UI초기화 주기 설정
                    _progress.postValue(_progress.value!! + 1)  //여기서 프로그래스 계산해서 넣어주기
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
}
