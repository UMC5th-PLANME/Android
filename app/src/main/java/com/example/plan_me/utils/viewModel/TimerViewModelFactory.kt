package com.example.plan_me.utils.viewModel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class TimerViewModelFactory(private val getResSharedPreferences: SharedPreferences, private val categoryIdSharedPreferences: SharedPreferences): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimerViewModel::class.java)) {
            return TimerViewModel(getResSharedPreferences, categoryIdSharedPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}