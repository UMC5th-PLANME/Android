package com.example.plan_me.ui.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import com.example.plan_me.databinding.FragmentTimerBreakSettingBinding

class TimerBreakFragment: Fragment() {
    private lateinit var binding: FragmentTimerBreakSettingBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTimerBreakSettingBinding.inflate(layoutInflater)
        initSetting()
        return binding.root
    }

    private fun initSetting() {
        // 바인딩을 사용하여 NumberPicker에 대한 참조 가져오기
        val min: NumberPicker = binding.timerBreakNumberPickerMin

        // 순환 막기
        min.wrapSelectorWheel = false

        // editText 설정 해제
        min.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        // 최소값 설정
        min.minValue = 0

        // 최대값 설정
        min.maxValue = 8

        // 보여질 값 설정 (string)
        min.displayedValues = arrayOf("10", "15", "20", "25", "30", "35", "40", "45", "50")
    }

    fun getBreakTime(): Int {
        // NumberPicker에서 설정된 값을 초로 변환하여 반환
        val min = binding.timerBreakNumberPickerMin.value * 10
        return min
    }
}